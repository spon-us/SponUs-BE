package com.sponus.sponusbe.domain.announcement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sponus.coredomain.domain.announcement.Announcement;
import com.sponus.coredomain.domain.announcement.AnnouncementImage;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementStatus;
import com.sponus.coredomain.domain.announcement.repository.AnnouncementRepository;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.propose.repository.ProposeRepository;
import com.sponus.coreinfraredis.entity.AnnouncementView;
import com.sponus.coreinfraredis.repository.AnnouncementViewRepository;
import com.sponus.coreinfraredis.util.RedisUtil;
import com.sponus.coreinfras3.S3Service;
import com.sponus.sponusbe.domain.announcement.dto.request.AnnouncementCreateRequest;
import com.sponus.sponusbe.domain.announcement.dto.request.AnnouncementUpdateRequest;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementCreateResponse;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementDetailResponse;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementStatusUpdateResponse;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementUpdateResponse;
import com.sponus.sponusbe.domain.announcement.exception.AnnouncementErrorCode;
import com.sponus.sponusbe.domain.announcement.exception.AnnouncementException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AnnouncementService {

	private final AnnouncementRepository announcementRepository;
	private final AnnouncementViewRepository announcementViewRepository;
	private final ProposeRepository proposeRepository;
	private final S3Service s3Service;
	private final RedisUtil redisUtil;

	public AnnouncementCreateResponse createAnnouncement(
		Organization authOrganization,
		AnnouncementCreateRequest request,
		List<MultipartFile> images
	) {
		final Announcement announcement = request.toEntity(authOrganization);
		updateAnnouncementImages(announcement, images);
		return AnnouncementCreateResponse.from(announcementRepository.save(announcement));
	}

	public AnnouncementDetailResponse getAnnouncement(Organization organization, Long announcementId) {
		Announcement announcement = announcementRepository.findById(announcementId)
			.orElseThrow(() -> new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_FOUND));

		AnnouncementView announcementView = announcementViewRepository.findById(announcementId.toString())
			.orElseGet(() -> AnnouncementView.builder().announcementId(announcementId.toString()).build());

		if (!announcementView.getOrganizationIds().contains(organization.getId().toString())) {
			announcementView.getOrganizationIds().add(organization.getId().toString());
			announcementViewRepository.save(announcementView);
		}

		redisUtil.appendToRecentlyViewedAnnouncement(organization.getEmail() + "_recently_viewed_list",
			String.valueOf(announcementId));

		if (proposeRepository.findByProposingOrganizationIdAndAnnouncementId(organization.getId(), announcementId)
			.isPresent()) {
			return AnnouncementDetailResponse.from(announcement, false);
		}

		return AnnouncementDetailResponse.from(announcement, true);
	}

	public void deleteAnnouncement(Organization organization, Long announcementId) {
		final Announcement announcement = announcementRepository.findById(announcementId)
			.orElseThrow(() -> new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_FOUND));
		if (!announcement.getWriter().getId().equals(organization.getId())) {
			throw new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_FOUND);
		}
		announcementRepository.delete(announcement);
	}

	public AnnouncementUpdateResponse updateAnnouncement(
		Organization authOrganization,
		Long announcementId,
		AnnouncementUpdateRequest request,
		List<MultipartFile> images
	) {
		final Announcement announcement = announcementRepository.findById(announcementId)
			.orElseThrow(() -> new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_FOUND));

		if (announcement.getStatus() != AnnouncementStatus.OPENED)
			throw new AnnouncementException(AnnouncementErrorCode.CLOSED_ANNOUNCEMENT_STATUS);
		if (!isOrganizationsAnnouncement(authOrganization.getId(), announcement))
			throw new AnnouncementException(AnnouncementErrorCode.INVALID_ORGANIZATION);

		announcement.updateInfo(
			request.title(),
			request.type(),
			request.category(),
			request.content()
		);

		// 공고는 이미지가 필수이므로, 이미지가 없는 경우에는 업데이트하지 않음
		if (images != null)
			updateAnnouncementImages(announcement, images);

		return AnnouncementUpdateResponse.from(announcement);
	}

	public AnnouncementUpdateResponse updateAnnouncementStatus(
		Organization authOrganization,
		Long announcementId,
		AnnouncementStatusUpdateResponse request) {
		final Announcement announcement = announcementRepository.findById(announcementId)
			.orElseThrow(() -> new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_FOUND));
		if (!isOrganizationsAnnouncement(authOrganization.getId(), announcement))
			throw new AnnouncementException(AnnouncementErrorCode.INVALID_ORGANIZATION);

		try {
			announcement.updateStatus(AnnouncementStatus.of(request.status()));
		} catch (Exception e) {
			throw new AnnouncementException(AnnouncementErrorCode.INVALID_ANNOUNCEMENT_STATUS);
		}
		return AnnouncementUpdateResponse.from(announcement);
	}

	public void updateAllViewedAnnouncementViewCount() {
		Iterable<AnnouncementView> announcementViews = announcementViewRepository.findAll();
		announcementViews.forEach(announcementView -> {
			Optional<Announcement> optionalAnnouncement = announcementRepository.findById(
				Long.parseLong(announcementView.getAnnouncementId()));
			if (optionalAnnouncement.isPresent()) {
				Announcement announcement = optionalAnnouncement.get();
				announcement.updateViewCount(announcementView.getOrganizationIds().size());
			}
		});
	}

	public void resetAllAnnouncementViewCount() {
		List<Announcement> announcements = announcementRepository.findAll();
		announcements.forEach(announcement -> announcement.updateViewCount(0L));
	}

	public void updateUpdatedAt(Long announcementId) {
		final Announcement announcement = announcementRepository.findById(announcementId)
			.orElseThrow(() -> new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_FOUND));
		announcement.setUpdatedAt(LocalDateTime.now());
	}

	private boolean isOrganizationsAnnouncement(Long organizationId, Announcement announcement) {
		return announcement.getWriter().getId().equals(organizationId);
	}

	private void updateAnnouncementImages(Announcement announcement, List<MultipartFile> images) {
		// 공고의 이미지는 반드시 존재해야함
		announcement.getAnnouncementImages().stream().forEach(image -> {
			s3Service.deleteFile(image.getUrl());
		});
		announcement.getAnnouncementImages().clear();
		images.forEach(image -> {
			final String url = s3Service.uploadFile(image);
			AnnouncementImage announcementImage = AnnouncementImage.builder()
				.name(image.getOriginalFilename())
				.url(url)
				.build();
			announcementImage.setAnnouncement(announcement);
		});
	}
}
