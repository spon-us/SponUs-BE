package com.sponus.sponusbe.domain.announcement.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.sponusbe.domain.announcement.dto.AnnouncementBriefResponse;
import com.sponus.sponusbe.domain.announcement.dto.AnnouncementCreateRequest;
import com.sponus.sponusbe.domain.announcement.dto.AnnouncementResponse;
import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.announcement.exception.AnnouncementErrorCode;
import com.sponus.sponusbe.domain.announcement.exception.AnnouncementException;
import com.sponus.sponusbe.domain.announcement.repository.AnnouncementRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AnnouncementQueryService {

	private final AnnouncementRepository announcementRepository;

	public AnnouncementResponse getAnnouncement(Long announcementId) {
		Announcement announcement = announcementRepository.findById(announcementId)
			.orElseThrow(() -> new AnnouncementException(AnnouncementErrorCode.ANNOUNCEMENT_ERROR));
		return AnnouncementResponse.from(announcement);
	}

	public List<AnnouncementResponse> searchAnnouncement(String keyword) {
		
		return announcementRepository.findByTitleContains(keyword).stream()
			.map(AnnouncementResponse::from).toList();
	}

	public AnnouncementBriefResponse createAnnouncement(AnnouncementCreateRequest request) {
		return null;
	}
}
