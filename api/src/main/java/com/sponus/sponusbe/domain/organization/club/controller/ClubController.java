package com.sponus.sponusbe.domain.organization.club.controller;

import static com.sponus.sponusbe.global.enums.ApiPath.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coreinfrasecurity.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.organization.club.dto.ClubGetResponse;
import com.sponus.sponusbe.domain.organization.club.dto.ClubUpdateRequest;
import com.sponus.sponusbe.domain.organization.club.service.ClubService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(CLUB_URI)
@RequiredArgsConstructor
public class ClubController {
	private final ClubService clubService;

	@GetMapping("/{clubId}")
	public ApiResponse<ClubGetResponse> getClub(@PathVariable Long clubId) {
		return ApiResponse.onSuccess(clubService.getClub(clubId));
	}

	@PatchMapping("/me")
	public ApiResponse<Void> updateClub(
		@AuthOrganization Organization authOrganization,
		@Valid @RequestBody ClubUpdateRequest request) {
		clubService.updateClub(authOrganization.getId(), request);
		return ApiResponse.onSuccess(null);
	}
}
