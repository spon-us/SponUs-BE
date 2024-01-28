package com.sponus.sponusbe.domain.announcement.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.sponusbe.domain.announcement.dto.request.AnnouncementCreateRequest;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementBriefResponse;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementResponse;
import com.sponus.sponusbe.domain.announcement.repository.AnnouncementRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AnnouncementQueryService {

	private final AnnouncementRepository announcementRepository;

	public List<AnnouncementResponse> searchAnnouncement(String keyword) {
		log.info("search announcement by keyword: {}", keyword);
		return announcementRepository.findByTitleContains(keyword).stream()
			.map(AnnouncementResponse::from).toList();
	}

	public AnnouncementBriefResponse createAnnouncement(AnnouncementCreateRequest request) {
		return null;
	}
}
