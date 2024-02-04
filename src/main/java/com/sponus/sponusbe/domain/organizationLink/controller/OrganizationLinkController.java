package com.sponus.sponusbe.domain.organizationLink.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
