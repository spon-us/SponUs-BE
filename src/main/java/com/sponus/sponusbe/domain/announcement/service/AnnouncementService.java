package com.sponus.sponusbe.domain.announcement.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sponus.sponusbe.domain.announcement.dto.request.AnnouncementCreateRequest;
import com.sponus.sponusbe.domain.announcement.dto.request.AnnouncementUpdateRequest;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementCreateResponse;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementDetailResponse;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementSummaryResponse;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementUpdateResponse;
import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.announcement.entity.AnnouncementImage;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementStatus;
import com.sponus.sponusbe.domain.announcement.exception.AnnouncementErrorCode;
import com.sponus.sponusbe.domain.announcement.exception.AnnouncementException;
import com.sponus.sponusbe.domain.announcement.repository.AnnouncementRepository;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.s3.S3Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AnnouncementService {

	private final AnnouncementRepository announcementRepository;
	private final S3Service s3Service;

	public AnnouncementCreateResponse createAnnouncement(
		Organization authOrganization,
		AnnouncementCreateRequest request,
		List<MultipartFile> images
	) {
		final Announcement announcement = request.toEntity(authOrganization);
		updateAnnouncementImages(announcement, images);
		return AnnouncementCreateResponse.from(announcementRepository.save(announcement));
	}

	public AnnouncementDetailResponse getAnnouncement(Long announcementId) {
		Announcement announcement = announcementRepository.findById(announcementId)
			.orElseThrow(() -> new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_FOUND));
		announcement.increaseViewCount();
		return AnnouncementDetailResponse.from(announcement);
	}

	public List<AnnouncementSummaryResponse> getListAnnouncement(AnnouncementStatus status) {
		List<Announcement> announcements = announcementRepository.findByStatus(status);
		return announcements.stream()
			.map(AnnouncementSummaryResponse::from)
			.toList();
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
		Long proposeId,
		AnnouncementUpdateRequest request,
		List<MultipartFile> images
	) {
		final Announcement announcement = announcementRepository.findById(proposeId)
			.orElseThrow(() -> new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_FOUND));

		if (announcement.getStatus() != AnnouncementStatus.OPENED)
			throw new AnnouncementException(AnnouncementErrorCode.INVALID_ANNOUNCEMENT_STATUS);
		if (!isOrganizationsAnnouncement(authOrganization.getId(), announcement))
			throw new AnnouncementException(AnnouncementErrorCode.INVALID_ORGANIZATION);

		announcement.updateInfo(
			request.title(),
			request.type(),
			request.category(),
			request.content(),
			request.status()
		);
		updateAnnouncementImages(announcement, images);
		announcementRepository.save(announcement);
		return AnnouncementUpdateResponse.from(announcement);
	}

	private boolean isOrganizationsAnnouncement(Long organizationId, Announcement announcement) {
		return announcement.getWriter().getId().equals(organizationId);
	}

	private void updateAnnouncementImages(Announcement announcement, List<MultipartFile> images) {
		// 공고의 이미지는 반드시 존재해야함
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
