package com.sponus.sponusbe.domain.announcement.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.sponusbe.auth.jwt.util.RedisUtil;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementSummaryResponse;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementCategory;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementType;
import com.sponus.sponusbe.domain.announcement.repository.AnnouncementRepository;
import com.sponus.sponusbe.domain.organization.entity.Organization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AnnouncementQueryService {

	private final AnnouncementRepository announcementRepository;
	private final RedisUtil redisUtil;

	public List<AnnouncementSummaryResponse> searchAnnouncement(String keyword) {
		log.info("search announcement by keyword: {}", keyword);
		return announcementRepository.findByTitleContains(keyword).stream()
			.map(AnnouncementSummaryResponse::from).toList();
	}

	public List<AnnouncementSummaryResponse> getAnnouncementByCategory(AnnouncementCategory category,
		AnnouncementType type) {
		// 둘 다 값이 있는 경우
		if (category != null && type != null) {
			log.info("category & type");
			return announcementRepository.findByCategoryAndType(category, type)
				.stream()
				.map(AnnouncementSummaryResponse::from)
				.toList();
		}
		// category 만 있는 경우
		else if (category != null) {
			return announcementRepository.findByCategory(category)
				.stream()
				.map(AnnouncementSummaryResponse::from)
				.toList();
		}
		// type 만 있는 경우
		else if (type != null) {
			return announcementRepository.findByType(type)
				.stream()
				.map(AnnouncementSummaryResponse::from)
				.toList();
		}
		// 둘 다 값이 없는 경우, 전체 announcement 반환
		else {
			log.info(announcementRepository.findAll().get(0).getTitle());
			return announcementRepository.findAll().stream().map(AnnouncementSummaryResponse::from).toList();
		}
	}

	public List<Object> getRecentlyViewedAnnouncement(Organization authOrganization) {
		return redisUtil.getList(authOrganization.getEmail() + "_recently_viewed_list");
	}

	public List<AnnouncementSummaryResponse> getPopularAnnouncement() {
		return announcementRepository.findTop10ByOrderByViewCountDesc().stream()
			.map(AnnouncementSummaryResponse::from)
			.toList();
	}
}
