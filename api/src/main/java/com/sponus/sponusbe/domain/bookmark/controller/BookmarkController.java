package com.sponus.sponusbe.domain.bookmark.controller;

import static com.sponus.sponusbe.global.enums.ApiPath.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coreinfrasecurity.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.bookmark.dto.request.BookmarkToggleRequest;
import com.sponus.sponusbe.domain.bookmark.dto.response.BookmarkToggleResponse;
import com.sponus.sponusbe.domain.bookmark.service.BookmarkQueryService;
import com.sponus.sponusbe.domain.bookmark.service.BookmarkService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(ORGANIZATION_URI)
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

	// TODO: 북마크 조회 기능
	// @GetMapping("/bookmarked")
	// public ApiResponse<List<BookmarkGetResponse>> getBookmark(
	// 	@AuthOrganization Organization authOrganization,
	// 	@RequestParam(name = "sort") BookmarkStatus sortStatus
	// ) {
	// 	if (sortStatus == BookmarkStatus.RECENT) {
	// 		return ApiResponse.onSuccess(bookmarkQueryService.getRecentBookmark(authOrganization));
	// 	}
	//
	// 	if (sortStatus == BookmarkStatus.VIEWED) {
	// 		return ApiResponse.onSuccess(bookmarkQueryService.getViewedBookmark(authOrganization));
	// 	}
	//
	// 	if (sortStatus == BookmarkStatus.SAVED) {
	// 		return ApiResponse.onSuccess(bookmarkQueryService.getSavedBookmark(authOrganization));
	// 	}
	// 	return ApiResponse.onSuccess(Collections.emptyList());
	// }
}
