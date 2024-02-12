package com.sponus.sponusbe.domain.propose.controller;

import java.io.IOException;
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

import com.sponus.sponusbe.auth.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.propose.dto.request.ProposeCreateRequest;
import com.sponus.sponusbe.domain.propose.dto.request.ProposeStatusUpdateRequest;
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

	@PostMapping(consumes = "multipart/form-data")
	public ApiResponse<ProposeCreateResponse> createPropose(
		@AuthOrganization Organization authOrganization,
		@RequestPart("request") @Valid ProposeCreateRequest request,
		@RequestPart(value = "attachments", required = false) List<MultipartFile> attachments
	) throws IOException {
		return ApiResponse.onSuccess(
			proposeService.createPropose(
				authOrganization,
				request,
				attachments == null ? List.of() : attachments
			)
		);
	}

	@GetMapping("/sent")
	public ApiResponse<List<ProposeSummaryGetResponse>> getSentProposes(
		@AuthOrganization Organization authOrganization
	) {
		return ApiResponse.onSuccess(proposeQueryService.getSentProposes(authOrganization));
	}

	@GetMapping("/received")
	public ApiResponse<List<ProposeSummaryGetResponse>> getReceivedProposes(
		@AuthOrganization Organization authOrganization,
		@RequestParam Long announcementId
	) {
		return ApiResponse.onSuccess(proposeQueryService.getReceivedProposes(authOrganization, announcementId));
	}

	@GetMapping("/{proposeId}")
	public ApiResponse<ProposeDetailGetResponse> getProposeDetail(
		@AuthOrganization Organization authOrganization,
		@PathVariable Long proposeId
	) {
		return ApiResponse.onSuccess(proposeService.getProposeDetail(authOrganization, proposeId));
	}

	@PatchMapping(value = "/{proposeId}", consumes = "multipart/form-data")
	public ApiResponse<Void> updatePropose(
		@AuthOrganization Organization authOrganization,
		@PathVariable Long proposeId,
		@RequestPart @Valid ProposeUpdateRequest request,
		@RequestPart(value = "attachments", required = false) List<MultipartFile> attachments
	) {
		proposeService.updatePropose(
			authOrganization,
			proposeId,
			request,
			attachments == null ? List.of() : attachments);
		return ApiResponse.onSuccess(null);
	}

	@PatchMapping(value = "/{proposeId}/status")
	public ApiResponse<Void> acceptPropose(
		@AuthOrganization Organization authOrganization,
		@PathVariable Long proposeId,
		@RequestBody @Valid ProposeStatusUpdateRequest request
	) {
		proposeService.updateProposeStatus(
			authOrganization,
			proposeId,
			request.status()
		);
		return ApiResponse.onSuccess(null);
	}

	@DeleteMapping("/{proposeId}")
	public ApiResponse<Void> deletePropose(
		@AuthOrganization Organization authOrganization,
		@PathVariable Long proposeId
	) {
		proposeService.deletePropose(authOrganization, proposeId);
		return ApiResponse.onSuccess(null);
	}

}
