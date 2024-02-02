package com.sponus.sponusbe.domain.organization.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.sponusbe.auth.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.organization.dto.OrganizationDetailGetResponse;
import com.sponus.sponusbe.domain.organization.dto.OrganizationJoinRequest;
import com.sponus.sponusbe.domain.organization.dto.OrganizationJoinResponse;
import com.sponus.sponusbe.domain.organization.dto.OrganizationUpdateRequest;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.organization.service.OrganizationQueryService;
import com.sponus.sponusbe.domain.organization.service.OrganizationService;
import com.sponus.sponusbe.global.common.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/organizations")
public class OrganizationController {

	private final OrganizationService organizationService;
	private final OrganizationQueryService organizationQueryService;

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

	@GetMapping("/me")
	public ApiResponse<OrganizationDetailGetResponse> getMyOrganization(@AuthOrganization Organization organization) {
		return ApiResponse.onSuccess(OrganizationDetailGetResponse.from(organization));
	}

	@PatchMapping("/me")
	public ApiResponse<Void> updateMyOrganization(
		@AuthOrganization Organization organization,
		@RequestBody @Valid OrganizationUpdateRequest request
	) {
		organizationService.updateOrganization(organization.getId(), request);
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
}
