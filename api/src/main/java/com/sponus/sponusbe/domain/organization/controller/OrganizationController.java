package com.sponus.sponusbe.domain.organization.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.sponusbe.domain.organization.company.dto.OrganizationGetResponse;
import com.sponus.sponusbe.domain.organization.dto.OrganizationCreateRequest;
import com.sponus.sponusbe.domain.organization.dto.OrganizationImageUploadResponse;
import com.sponus.sponusbe.domain.organization.service.OrganizationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/organizations")
@RequiredArgsConstructor
public class OrganizationController {
	private final OrganizationService organizationService;

	@PostMapping("/join")
	public ApiResponse<Long> join(@RequestBody OrganizationCreateRequest request) {
		return ApiResponse.onSuccess(organizationService.createOrganization(request));
	}

	@GetMapping
	public ApiResponse<PageResponse<OrganizationGetResponse>> getOrganizations(
		@ModelAttribute @Valid PageCondition pageCondition,
		@ModelAttribute @Valid OrganizationType organizationType) {
		return ApiResponse.onSuccess(organizationService.getOrganizations(pageCondition, organizationType));
	}

	@PostMapping(value = "/{organizationId}/profileImage", consumes = "multipart/form-data")
	public ApiResponse<OrganizationImageUploadResponse> uploadProfileImage(
		@PathVariable Long organizationId,
		@RequestPart(name = "profileImage") MultipartFile file) {
		return ApiResponse.onSuccess(organizationService.uploadProfileImage(organizationId, file));
	}

	@GetMapping("/exists")
	public ApiResponse<Boolean> verifyName(@RequestParam String name) {
		return ApiResponse.onSuccess(organizationService.verifyName(name));
	}

	@DeleteMapping("/{organizationId}")
	public ApiResponse<Void> deleteOrganization(@PathVariable Long organizationId) {
		organizationService.deleteOrganization(organizationId);
		return ApiResponse.onSuccess(null);
	}
}
