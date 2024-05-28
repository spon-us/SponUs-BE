package com.sponus.sponusbe.domain.bookmark.controller;

import static com.sponus.sponusbe.global.enums.ApiPath.*;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coreinfrasecurity.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.bookmark.dto.request.BookmarkToggleRequest;
import com.sponus.sponusbe.domain.bookmark.dto.response.BookmarkGetResponse;
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

	@GetMapping("/bookmarked")
	public ApiResponse<List<BookmarkGetResponse>> getBookmark(
		@AuthOrganization Organization authOrganization,
		@RequestParam(name = "sort") String sort
	) {
		if (sort.equals("RECENT")) {
			return ApiResponse.onSuccess(bookmarkQueryService.getRecentBookmark(authOrganization));
		}

		if (sort.equals("COMPANY")) {
			return ApiResponse.onSuccess(bookmarkQueryService.getCompanyBookmark(authOrganization));
		}

		if (sort.equals("CLUB")) {
			return ApiResponse.onSuccess(bookmarkQueryService.getClubBookmark(authOrganization));
		}
		return ApiResponse.onSuccess(Collections.emptyList());
	}
}
