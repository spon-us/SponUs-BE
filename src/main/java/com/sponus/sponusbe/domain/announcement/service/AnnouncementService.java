package com.sponus.sponusbe.domain.announcement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.sponusbe.domain.announcement.dto.request.AnnouncementCreateRequest;
import com.sponus.sponusbe.domain.announcement.dto.request.AnnouncementUpdateRequest;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementCreateResponse;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementResponse;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementUpdateResponse;
import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementStatus;
import com.sponus.sponusbe.domain.announcement.exception.AnnouncementErrorCode;
import com.sponus.sponusbe.domain.announcement.exception.AnnouncementException;
import com.sponus.sponusbe.domain.announcement.repository.AnnouncementRepository;
import com.sponus.sponusbe.domain.organization.entity.Organization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AnnouncementService {

	private final AnnouncementRepository announcementRepository;

	public AnnouncementCreateResponse createAnnouncement(
		Organization authOrganization,
		AnnouncementCreateRequest request) {
		final Announcement announcement = announcementRepository.save(request.toEntity(authOrganization));
		return AnnouncementCreateResponse.from(announcement);
	}

	public AnnouncementResponse getAnnouncement(Long announcementId) {
		Announcement announcement = announcementRepository.findById(announcementId)
			.orElseThrow(() -> new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_FOUND));
		announcement.increaseViewCount();
		return AnnouncementResponse.from(announcement);
	}

	public List<AnnouncementResponse> getListAnnouncement(AnnouncementStatus status) {
		List<Announcement> announcements = announcementRepository.findByStatus(status);
		return announcements.stream()
			.map(AnnouncementResponse::from)
			.collect(Collectors.toList());
	}

	public void deleteAnnouncement(Organization organization, Long announcementId) {
		final Announcement announcement = announcementRepository.findById(announcementId)
			.orElseThrow(() -> new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_FOUND));
		if (!announcement.getWriter().getId().equals(organization.getId())) {
			throw new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_FOUND);
		}
		announcementRepository.delete(announcement);
	}

	public AnnouncementUpdateResponse updateAnnouncement(Organization authOrganization, Long proposeId,
		AnnouncementUpdateRequest request) {
		final Announcement announcement = announcementRepository.findById(proposeId)
			.orElseThrow(() -> new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_NOT_FOUND));

		if (!isOrganizationsAnnouncement(authOrganization.getId(), announcement))
			throw new AnnouncementException(AnnouncementErrorCode.INVALID_ORGANIZATION);

		announcement.update(request.title(), request.type(), request.category(), request.content());
		announcementRepository.save(announcement);
		return AnnouncementUpdateResponse.from(announcement);
	}

	private boolean isOrganizationsAnnouncement(Long organizationId, Announcement announcement) {
		return announcement.getWriter().getId().equals(organizationId);
	}

}
