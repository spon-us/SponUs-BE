package com.sponus.sponusbe.domain.organization.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.sponusbe.auth.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.organization.dto.BookmarkToggleRequest;
import com.sponus.sponusbe.domain.organization.dto.BookmarkToggleResponse;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.organization.service.BookmarkService;
import com.sponus.sponusbe.global.common.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/me/announcement")
public class BookmarkController {

	private final BookmarkService bookmarkService;

	@PostMapping("/{organizationId}/bookmarked")
	public ApiResponse<BookmarkToggleResponse> bookmarkToggle(
		@AuthOrganization Organization authOrganization,
		@RequestBody BookmarkToggleRequest request
	) {
		return ApiResponse.onSuccess(bookmarkService.bookmarkToggle(authOrganization, request));
	}
}
