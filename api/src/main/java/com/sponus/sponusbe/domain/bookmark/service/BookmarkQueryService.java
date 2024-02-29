package com.sponus.sponusbe.domain.bookmark.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.bookmark.Bookmark;
import com.sponus.coredomain.domain.bookmark.repository.BookmarkRepository;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.sponusbe.domain.bookmark.dto.BookmarkGetResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BookmarkQueryService {

	private final BookmarkRepository bookmarkRepository;

	public List<BookmarkGetResponse> getRecentBookmark(Organization organization) {
		return bookmarkRepository.findByOrganizationOrderByCreatedAtDesc(organization)
			.stream()
			.map(bookmark -> BookmarkGetResponse.from(bookmark.getAnnouncement(), bookmark))
			.toList();
	}

	public List<BookmarkGetResponse> getViewedBookmark(Organization organization) {
		List<Bookmark> bookmarks = bookmarkRepository.findByOrganizationOrderByAnnouncementViewCountDesc(organization);
		return bookmarks.stream()
			.map(bookmark -> BookmarkGetResponse.from(bookmark.getAnnouncement(), bookmark))
			.toList();
	}

	public List<BookmarkGetResponse> getSavedBookmark(Organization organization) {
		List<Bookmark> bookmarks = bookmarkRepository.findByOrganizationOrderBySaveCountDesc(organization);
		return bookmarks.stream()
			.map(bookmark -> BookmarkGetResponse.from(bookmark.getAnnouncement(), bookmark))
			.toList();
	}
}
