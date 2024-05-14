package com.sponus.sponusbe.domain.organizationLink.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coreinfrasecurity.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.organizationLink.dto.request.OrganizationLinkCreateRequest;
import com.sponus.sponusbe.domain.organizationLink.dto.request.OrganizationLinkUpdateRequest;
import com.sponus.sponusbe.domain.organizationLink.dto.response.OrganizationLinkCreateResponse;
import com.sponus.sponusbe.domain.organizationLink.dto.response.OrganizationLinkGetResponse;
import com.sponus.sponusbe.domain.organizationLink.service.OrganizationLinkQueryService;
import com.sponus.sponusbe.domain.organizationLink.service.OrganizationLinkService;

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
	public ApiResponse<OrganizationLinkGetResponse> getOrganizationLink(
		@PathVariable("organizationLinkId") Long organizationLinkId) {
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
