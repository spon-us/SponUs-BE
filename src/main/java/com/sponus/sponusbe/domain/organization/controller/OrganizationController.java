package com.sponus.sponusbe.domain.organization.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.sponusbe.auth.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.organization.dto.OrganizationJoinRequest;
import com.sponus.sponusbe.domain.organization.dto.OrganizationJoinResponse;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.organization.service.OrganizationService;
import com.sponus.sponusbe.global.common.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/organizations")
public class OrganizationController {

	private final OrganizationService organizationService;

	@PostMapping("/join")
	public ApiResponse<OrganizationJoinResponse> join(@Valid @RequestBody OrganizationJoinRequest request) {
		OrganizationJoinResponse response = organizationService.join(request);
		return ApiResponse.onSuccess(response);
	}

	@GetMapping("/test")
	public ApiResponse<Long> test(@AuthOrganization Organization organization) {
		Long id = organization.getId();
		return ApiResponse.onSuccess(id);
	}
}
