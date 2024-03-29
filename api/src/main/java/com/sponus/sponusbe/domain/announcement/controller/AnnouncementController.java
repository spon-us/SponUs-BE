package com.sponus.sponusbe.domain.announcement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sponus.coredomain.domain.announcement.enums.AnnouncementCategory;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementType;
import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coreinfrasecurity.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.announcement.dto.request.AnnouncementCreateRequest;
import com.sponus.sponusbe.domain.announcement.dto.request.AnnouncementUpdateRequest;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementCreateResponse;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementDetailResponse;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementStatusUpdateResponse;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementSummaryResponse;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementUpdateResponse;
import com.sponus.sponusbe.domain.announcement.service.AnnouncementQueryService;
import com.sponus.sponusbe.domain.announcement.service.AnnouncementService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/announcements")
@RestController
public class AnnouncementController {

	private final AnnouncementQueryService announcementQueryService;
	private final AnnouncementService announcementService;

	@GetMapping("/recommend")
	public ApiResponse<List<AnnouncementSummaryResponse>> getRecommendAnnouncement() {
		return ApiResponse.onSuccess(announcementQueryService.getRecommendAnnouncement());
	}

	@GetMapping("/popular")
	public ApiResponse<List<AnnouncementSummaryResponse>> getPopularAnnouncement() {
		return ApiResponse.onSuccess(announcementQueryService.getPopularAnnouncement());
	}

	@GetMapping("/{announcementId}")
	public ApiResponse<AnnouncementDetailResponse> getAnnouncement(
		@PathVariable("announcementId") Long announcementId,
		@AuthOrganization Organization authOrganization
	) {
		return ApiResponse.onSuccess(announcementService.getAnnouncement(authOrganization, announcementId));
	}

	// @GetMapping("/status")
	// public ApiResponse<List<AnnouncementSummaryResponse>> getListAnnouncement(
	// 	@RequestParam("status") AnnouncementStatus status) {
	// 	return ApiResponse.onSuccess(announcementQueryService.getListAnnouncement(status));
	// }

	@GetMapping("/me/opened")
	public ApiResponse<List<AnnouncementSummaryResponse>> getMyOpenedAnnouncement(
		@AuthOrganization Organization authOrganization) {
		return ApiResponse.onSuccess(announcementQueryService.getMyOpenedAnnouncement(authOrganization));
	}

	@GetMapping("/me")
	public ApiResponse<List<AnnouncementSummaryResponse>> getMyAnnouncement(
		@AuthOrganization Organization authOrganization) {
		return ApiResponse.onSuccess(announcementQueryService.getMyAnnouncement(authOrganization));
	}

	@GetMapping
	public ApiResponse<List<AnnouncementSummaryResponse>> searchAnnouncement(@RequestParam("search") String keyword) {
		return ApiResponse.onSuccess(announcementQueryService.searchAnnouncement(keyword));
	}

	@PostMapping(consumes = "multipart/form-data")
	public ApiResponse<AnnouncementCreateResponse> createAnnouncement(
		@AuthOrganization Organization authOrganization,
		@Valid @RequestPart("request") AnnouncementCreateRequest request,
		@RequestPart(value = "images") List<MultipartFile> images
	) {
		return ApiResponse.onSuccess(
			announcementService.createAnnouncement(
				authOrganization,
				request,
				images
			)
		);
	}

	@DeleteMapping("/{announcementId}")
	public ApiResponse<Void> deleteAnnouncement(
		@AuthOrganization Organization authOrganization,
		@PathVariable Long announcementId) {
		announcementService.deleteAnnouncement(authOrganization, announcementId);
		return ApiResponse.onSuccess(null);
	}

	@PatchMapping(value = "/{announcementId}", consumes = "multipart/form-data")
	public ApiResponse<AnnouncementUpdateResponse> updateAnnouncement(
		@AuthOrganization Organization authOrganization,
		@PathVariable Long announcementId,
		@RequestPart("request") @Valid AnnouncementUpdateRequest request,
		@RequestPart(value = "images", required = false) @Valid List<MultipartFile> images
	) {
		return ApiResponse.onSuccess(announcementService.updateAnnouncement(
			authOrganization,
			announcementId,
			request,
			images
		));
	}

	@PatchMapping("/{announcementId}/status")
	public ApiResponse<AnnouncementUpdateResponse> updateAnnouncementStatus(
		@AuthOrganization Organization authOrganization,
		@PathVariable Long announcementId,
		@RequestBody @Valid AnnouncementStatusUpdateResponse request
	) {
		return ApiResponse.onSuccess(
			announcementService.updateAnnouncementStatus(authOrganization, announcementId, request));
	}

	@GetMapping("/category")
	public ApiResponse<List<AnnouncementSummaryResponse>> getAnnouncementByCategory(
		@RequestParam(value = "category", required = false) AnnouncementCategory category,
		@RequestParam(value = "type", required = false) AnnouncementType type
	) {
		log.info(String.valueOf(category));
		return ApiResponse.onSuccess(
			announcementQueryService.getAnnouncementByCategory(category, type));
	}

	@GetMapping("/recently_viewed_announcements")
	public ApiResponse<List<Object>> getRecentAnnouncement(
		@AuthOrganization Organization authOrganization
	) {
		return ApiResponse.onSuccess(
			announcementQueryService.getRecentlyViewedAnnouncement(authOrganization)
		);
	}

	@PatchMapping("/{announcementId}/pullUp")
	public ApiResponse<Void> pullUpAnnouncement(@PathVariable("announcementId") Long announcementId) {
		announcementService.updateUpdatedAt(announcementId);
		return ApiResponse.onSuccess(null);
	}
}
