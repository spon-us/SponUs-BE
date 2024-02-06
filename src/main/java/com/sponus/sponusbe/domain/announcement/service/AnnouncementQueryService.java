package com.sponus.sponusbe.domain.announcement.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementSummaryResponse;
import com.sponus.sponusbe.domain.announcement.repository.AnnouncementRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AnnouncementQueryService {

	private final AnnouncementRepository announcementRepository;

	public List<AnnouncementSummaryResponse> searchAnnouncement(String keyword) {
		log.info("search announcement by keyword: {}", keyword);
		return announcementRepository.findByTitleContains(keyword).stream()
			.map(AnnouncementSummaryResponse::from).toList();
	}
}
