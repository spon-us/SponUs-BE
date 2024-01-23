package com.sponus.sponusbe.domain.propose.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.sponusbe.auth.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.propose.service.ProposeService;
import com.sponus.sponusbe.global.common.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProposeController {

	private final ProposeService proposeService;

	@PostMapping("/api/v1/propose")
	public ApiResponse<ProposeCreateResponse> createPropose(
		@AuthOrganization Organization organization,
		@RequestBody @Valid ProposeCreateRequest request
	) {
		return ApiResponse.onSuccess(proposeService.createPropose(organization, request));
	}
}
