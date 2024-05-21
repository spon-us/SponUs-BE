package com.sponus.sponusbe.domain.organization.club;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.coredomain.domain.common.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/clubs")
@RequiredArgsConstructor
public class ClubController {
	private final ClubService clubService;

	@GetMapping("/{clubId}")
	public ApiResponse<ClubGetResponse> getClub(@PathVariable Long clubId) {
		return ApiResponse.onSuccess(clubService.getClub(clubId));
	}

	@PatchMapping("/{clubId}")
	public ApiResponse<Void> updateClub(
		@PathVariable Long clubId,
		@Valid @RequestBody ClubUpdateRequest request) {
		clubService.updateClub(clubId, request);
		return ApiResponse.onSuccess(null);
	}
}
