package com.sponus.sponusbe.domain.organization.controller;

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

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coreinfrasecurity.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.notification.dto.response.NotificationSummaryResponse;
import com.sponus.sponusbe.domain.organization.dto.OrganizationDetailGetResponse;
import com.sponus.sponusbe.domain.organization.dto.OrganizationJoinRequest;
import com.sponus.sponusbe.domain.organization.dto.OrganizationJoinResponse;
import com.sponus.sponusbe.domain.organization.dto.OrganizationSummaryResponse;
import com.sponus.sponusbe.domain.organization.dto.OrganizationUpdateRequest;
import com.sponus.sponusbe.domain.organization.service.OrganizationQueryService;
import com.sponus.sponusbe.domain.organization.service.OrganizationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/organizations")
public class OrganizationController {

	private final OrganizationService organizationService;
	private final OrganizationQueryService organizationQueryService;

	@PostMapping(value = "/join")
	public ApiResponse<OrganizationJoinResponse> join(
		@RequestBody OrganizationJoinRequest request) {
		OrganizationJoinResponse response = organizationService.join(request);
		return ApiResponse.onSuccess(response);
	}

	@GetMapping("/test")
	public ApiResponse<Long> test(@AuthOrganization Organization organization) {
		Long id = organization.getId();
		return ApiResponse.onSuccess(id);
	}

	@GetMapping("/me")
	public ApiResponse<OrganizationDetailGetResponse> getMyOrganization(@AuthOrganization Organization organization) {
		return ApiResponse.onSuccess(OrganizationDetailGetResponse.from(organization));
	}

	@PatchMapping(value = "/me", consumes = "multipart/form-data")
	public ApiResponse<Void> updateMyOrganization(
		@AuthOrganization Organization organization,
		@RequestPart @Valid OrganizationUpdateRequest request,
		@RequestPart(value = "attachments", required = false) MultipartFile attachment
	) {
		organizationService.updateOrganization(organization.getId(), request, attachment);
		return ApiResponse.onSuccess(null);
	}

	@DeleteMapping("/me")
	public ApiResponse<Void> deleteMyOrganization(@AuthOrganization Organization organization) {
		organizationService.deactivateOrganization(organization.getId());
		return ApiResponse.onSuccess(null);
	}

	@GetMapping("/{organizationId}")
	public ApiResponse<OrganizationDetailGetResponse> getOrganization(@PathVariable Long organizationId) {
		return ApiResponse.onSuccess(organizationQueryService.getOrganization(organizationId));
	}

	//이메일 인증
	@PostMapping("/email")
	public ApiResponse<String> sendEmail(@RequestParam("email") String email) throws Exception {
		return ApiResponse.onSuccess(organizationService.sendEmail(email));
	}

	@GetMapping
	public ApiResponse<List<OrganizationSummaryResponse>> searchOrganization(@RequestParam("search") String keyword) {
		return ApiResponse.onSuccess(organizationService.searchOrganization(keyword));
	}

	@GetMapping("/notifications")
	public ApiResponse<List<NotificationSummaryResponse>> getNotifications(
		@AuthOrganization Organization organization) {
		return ApiResponse.onSuccess(organizationQueryService.getNotifications(organization));
	}

	@DeleteMapping("/notifications/{notificationId}")
	public ApiResponse<Void> deleteNotification(
		@AuthOrganization Organization organization,
		@PathVariable("notificationId") Long notificationId) {
		organizationService.deleteNotification(organization, notificationId);
		return ApiResponse.onSuccess(null);
	}

	@PatchMapping("/notifications/{notificationId}")
	public ApiResponse<Void> readNotification(
		@AuthOrganization Organization organization,
		@PathVariable("notificationId") Long notificationId) {
		organizationService.readNotification(organization, notificationId);
		return ApiResponse.onSuccess(null);
	}
}
