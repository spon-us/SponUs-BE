package com.sponus.sponusbe.domain.propose.controller;

import static com.sponus.sponusbe.global.enums.ApiPath.*;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coreinfrasecurity.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.propose.dto.request.ProposeCreateRequest;
import com.sponus.sponusbe.domain.propose.dto.response.ProposeCreateResponse;
import com.sponus.sponusbe.domain.propose.dto.response.ProposeGetResponse;
import com.sponus.sponusbe.domain.propose.service.ProposeQueryService;
import com.sponus.sponusbe.domain.propose.service.ProposeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(PROPOSE_URI)
@RestController
public class ProposeController {

	private final ProposeService proposeService;
	private final ProposeQueryService proposequeryService;

	@PostMapping()
	public ApiResponse<ProposeCreateResponse> createPropose(
		@AuthOrganization Organization authOrganization,
		@RequestBody @Valid ProposeCreateRequest request
	) {
		return ApiResponse.onSuccess(proposeService.createPropose(authOrganization, request));
	}

	@GetMapping("/send")
	public ApiResponse<List<ProposeGetResponse>> getSendPropose(
		@AuthOrganization Organization authOrganization
	) {
		return ApiResponse.onSuccess(proposequeryService.getSendPropose(authOrganization));
	}
}
