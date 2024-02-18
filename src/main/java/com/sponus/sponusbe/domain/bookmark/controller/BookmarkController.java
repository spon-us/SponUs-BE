package com.sponus.sponusbe.domain.bookmark.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.sponusbe.auth.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.bookmark.dto.BookmarkGetResponse;
import com.sponus.sponusbe.domain.bookmark.dto.BookmarkToggleRequest;
import com.sponus.sponusbe.domain.bookmark.dto.BookmarkToggleResponse;
import com.sponus.sponusbe.domain.bookmark.entity.BookmarkStatus;
import com.sponus.sponusbe.domain.bookmark.service.BookmarkQueryService;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.bookmark.service.BookmarkService;
import com.sponus.sponusbe.global.common.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/me/announcements")
public class BookmarkController {

	private final BookmarkService bookmarkService;
	private final BookmarkQueryService bookmarkQueryService;

	@PostMapping("/bookmarked")
	public ApiResponse<BookmarkToggleResponse> bookmarkToggle(
		@AuthOrganization Organization authOrganization,
		@RequestBody BookmarkToggleRequest request
	) {
		return ApiResponse.onSuccess(bookmarkService.bookmarkToggle(authOrganization, request));
	}

	@GetMapping("/bookmarked")
	public ApiResponse<List<BookmarkGetResponse>> getBookmark(
		@AuthOrganization Organization authOrganization,
		@RequestParam(name = "sort") BookmarkStatus sortStatus
	) {
		if (sortStatus == BookmarkStatus.RECENT) {
			return ApiResponse.onSuccess(bookmarkQueryService.getRecentBookmark(authOrganization));
		}

		if (sortStatus == BookmarkStatus.VIEWED) {
			return ApiResponse.onSuccess(bookmarkQueryService.getViewedBookmark(authOrganization));
		}

		if (sortStatus == BookmarkStatus.SAVED) {
			return ApiResponse.onSuccess(bookmarkQueryService.getSavedBookmark(authOrganization));
		}
		return ApiResponse.onSuccess(Collections.emptyList());
	}
}
