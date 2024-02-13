package com.sponus.sponusbe.domain.bookmark.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.sponusbe.domain.announcement.repository.AnnouncementRepository;
import com.sponus.sponusbe.domain.bookmark.dto.BookmarkGetResponse;
import com.sponus.sponusbe.domain.bookmark.entity.Bookmark;
import com.sponus.sponusbe.domain.bookmark.repository.BookmarkRepository;
import com.sponus.sponusbe.domain.organization.entity.Organization;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BookmarkQueryService {

	private final BookmarkRepository bookmarkRepository;
	private final AnnouncementRepository announcementRepository;

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
