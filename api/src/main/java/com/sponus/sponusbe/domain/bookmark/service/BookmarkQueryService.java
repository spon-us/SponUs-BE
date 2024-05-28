package com.sponus.sponusbe.domain.bookmark.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.bookmark.repository.BookmarkRepository;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.sponusbe.domain.bookmark.dto.response.BookmarkGetResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BookmarkQueryService {

	private final BookmarkRepository bookmarkRepository;

	public List<BookmarkGetResponse> getRecentBookmark(Organization organization) {
		return null;
	}

	public List<BookmarkGetResponse> getViewedBookmark(Organization organization) {
		return null;
	}

	public List<BookmarkGetResponse> getSavedBookmark(Organization organization) {
		return null;
	}
}
