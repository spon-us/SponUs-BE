package com.sponus.sponusbe.domain.announcement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.sponusbe.auth.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.announcement.dto.AnnouncementBriefResponse;
import com.sponus.sponusbe.domain.announcement.dto.AnnouncementCreateRequest;
import com.sponus.sponusbe.domain.announcement.dto.AnnouncementCreateResponse;
import com.sponus.sponusbe.domain.announcement.dto.AnnouncementResponse;
import com.sponus.sponusbe.domain.announcement.service.AnnouncementQueryService;
import com.sponus.sponusbe.domain.announcement.service.AnnouncementService;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.global.common.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/announcement")
@RestController
public class AnnouncementController {

	private final AnnouncementQueryService announcementQueryService;
	private final AnnouncementService announcementService;

	@GetMapping("/recommend")
	public ApiResponse<?> getRecommendAnnouncement() {
		return null;
	}

	@GetMapping("/popular")
	public ApiResponse<?> getAnnouncement() {
		return null;
	}

	@GetMapping("/{announcementId}")
	public ApiResponse<AnnouncementResponse> getAnnouncement(@PathVariable Long announcementId) {
		return ApiResponse.onSuccess(announcementService.getAnnouncement(announcementId));
	}

	@GetMapping
	public ApiResponse<List<AnnouncementResponse>> searchAnnouncement(@RequestParam("search") String keyword) {
		return ApiResponse.onSuccess(announcementQueryService.searchAnnouncement(keyword));
	}

	@PostMapping("/")
	public ApiResponse<AnnouncementCreateResponse> createAnnouncement(@AuthOrganization Organization authOrganization,
		AnnouncementCreateRequest request) {
		return ApiResponse.onSuccess(announcementService.createAnnouncement(authOrganization, request));
	}

	@DeleteMapping("/{announcementId}")
	public ApiResponse<Void> deleteAnnouncement(
		@AuthOrganization Organization authOrganization,
		@PathVariable Long announcementId) {
		announcementService.deleteAnnouncement(authOrganization, announcementId);
		return ApiResponse.onSuccess(null);
	}

	@PatchMapping("/{announcementId}")
	public ApiResponse<?> updateAnnouncement(@PathVariable Long announcementId) {
		return null;
	}

}
