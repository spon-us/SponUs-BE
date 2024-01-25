package com.sponus.sponusbe.domain.announcement.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.sponusbe.domain.announcement.dto.AnnouncementCreateRequest;
import com.sponus.sponusbe.domain.announcement.dto.AnnouncementCreateResponse;
import com.sponus.sponusbe.domain.announcement.entity.Announcement;
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

	public AnnouncementCreateResponse createAnnouncement(Organization authOrganization,
		AnnouncementCreateRequest request) {
		final Announcement announcement = announcementRepository.save(request.toEntity(authOrganization));
		return AnnouncementCreateResponse.from(announcement);
	}
}
