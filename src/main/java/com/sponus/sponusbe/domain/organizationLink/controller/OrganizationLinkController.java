package com.sponus.sponusbe.domain.organizationLink.controller;

import com.sponus.sponusbe.domain.organizationLink.dto.request.OrganizationLinkUpdateRequest;
import com.sponus.sponusbe.domain.organizationLink.dto.response.OrganizationLinkGetResponse;
import org.springframework.web.bind.annotation.*;

import com.sponus.sponusbe.auth.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.organizationLink.dto.request.OrganizationLinkCreateRequest;
import com.sponus.sponusbe.domain.organizationLink.dto.response.OrganizationLinkCreateResponse;
import com.sponus.sponusbe.domain.organizationLink.service.OrganizationLinkQueryService;
import com.sponus.sponusbe.domain.organizationLink.service.OrganizationLinkService;
import com.sponus.sponusbe.global.common.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/organization-links")
@RestController
public class OrganizationLinkController {
	private final OrganizationLinkService organizationLinkService;
	private final OrganizationLinkQueryService organizationLinkQueryService;

	@PostMapping
	public ApiResponse<OrganizationLinkCreateResponse> createOrganizationLink(
		@AuthOrganization Organization organization,
		@RequestBody OrganizationLinkCreateRequest request) {

		return ApiResponse.onSuccess(organizationLinkService.createOrganizationLink(organization.getId(), request));
	}

	@GetMapping("/{organizationLinkId}")
	public ApiResponse<OrganizationLinkGetResponse> getOrganizationLink(@PathVariable("organizationLinkId") Long organizationLinkId) {
		return ApiResponse.onSuccess(organizationLinkQueryService.getOrganizationLink(organizationLinkId));
	}

	@PatchMapping("/{organizationLinkId}")
	public ApiResponse<Void> updateOrganizationLink(@PathVariable("organizationLinkId") Long organizationLinkId,
													@RequestBody OrganizationLinkUpdateRequest request) {
		organizationLinkService.updateOrganizationLink(organizationLinkId, request);
		return ApiResponse.onSuccess(null);
	}

	@DeleteMapping("/{organizationLinkId}")
	public ApiResponse<Void> deleteOrganizationLink(@PathVariable("organizationLinkId") Long organizationLinkId) {
		organizationLinkService.deleteOrganizationLink(organizationLinkId);
		return ApiResponse.onSuccess(null);
	}
}