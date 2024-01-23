package com.sponus.sponusbe.domain.propose.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.sponusbe.auth.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.propose.controller.dto.request.ProposeCreateRequest;
import com.sponus.sponusbe.domain.propose.controller.dto.request.ProposeGetCondition;
import com.sponus.sponusbe.domain.propose.controller.dto.response.ProposeCreateResponse;
import com.sponus.sponusbe.domain.propose.controller.dto.response.ProposeDetailGetResponse;
import com.sponus.sponusbe.domain.propose.controller.dto.response.ProposeSummaryGetResponse;
import com.sponus.sponusbe.domain.propose.service.ProposeQueryService;
import com.sponus.sponusbe.domain.propose.service.ProposeService;
import com.sponus.sponusbe.global.common.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProposeController {

	private final ProposeService proposeService;
	private final ProposeQueryService proposeQueryService;

	@PostMapping("/api/v1/propose")
	public ApiResponse<ProposeCreateResponse> createPropose(
		@AuthOrganization Organization authOrganization,
		@RequestBody @Valid ProposeCreateRequest request
	) {
		return ApiResponse.onSuccess(proposeService.createPropose(authOrganization, request));
	}

	@GetMapping("/api/v1/propose/me")
	public ApiResponse<List<ProposeSummaryGetResponse>> getMyProposes(
		@AuthOrganization Organization authOrganization,
		@ModelAttribute @Valid ProposeGetCondition condition
	) {
		return ApiResponse.onSuccess(proposeQueryService.getProposes(authOrganization, condition));
	}

	@GetMapping("/api/v1/propose/{proposeId}")
	public ApiResponse<ProposeDetailGetResponse> getProposeDetail(@PathVariable Long proposeId) {
		return ApiResponse.onSuccess(proposeQueryService.getProposeDetail(proposeId));
	}
}
