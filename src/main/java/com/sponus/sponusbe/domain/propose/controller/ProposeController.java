package com.sponus.sponusbe.domain.propose.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.sponusbe.auth.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.propose.dto.request.ProposeCreateRequest;
import com.sponus.sponusbe.domain.propose.dto.request.ProposeGetCondition;
import com.sponus.sponusbe.domain.propose.dto.request.ProposeUpdateRequest;
import com.sponus.sponusbe.domain.propose.dto.response.ProposeCreateResponse;
import com.sponus.sponusbe.domain.propose.dto.response.ProposeDetailGetResponse;
import com.sponus.sponusbe.domain.propose.dto.response.ProposeSummaryGetResponse;
import com.sponus.sponusbe.domain.propose.service.ProposeQueryService;
import com.sponus.sponusbe.domain.propose.service.ProposeService;
import com.sponus.sponusbe.global.common.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/proposes")
@RestController
public class ProposeController {

	private final ProposeService proposeService;
	private final ProposeQueryService proposeQueryService;

	@PostMapping
	public ApiResponse<ProposeCreateResponse> createPropose(
		@AuthOrganization Organization authOrganization,
		@RequestBody @Valid ProposeCreateRequest request
	) {
		return ApiResponse.onSuccess(proposeService.createPropose(authOrganization, request));
	}

	// TODO QueryDsl 변경 테스트 후 수정 필요
	@GetMapping("/me")
	public ApiResponse<List<ProposeSummaryGetResponse>> getMyProposes(
		@AuthOrganization Organization authOrganization,
		@ModelAttribute @Valid ProposeGetCondition condition
	) {
		return ApiResponse.onSuccess(proposeQueryService.getProposes(authOrganization, condition));
	}

	@GetMapping("/{proposeId}")
	public ApiResponse<ProposeDetailGetResponse> getProposeDetail(@PathVariable Long proposeId) {
		return ApiResponse.onSuccess(proposeQueryService.getProposeDetail(proposeId));
	}

	@PatchMapping("/{proposeId}")
	public ApiResponse<Void> updatePropose(
		@AuthOrganization Organization authOrganization,
		@PathVariable Long proposeId,
		@RequestBody @Valid ProposeUpdateRequest request
	) {
		proposeService.updatePropose(authOrganization, proposeId, request);
		return ApiResponse.onSuccess(null);
	}
}
