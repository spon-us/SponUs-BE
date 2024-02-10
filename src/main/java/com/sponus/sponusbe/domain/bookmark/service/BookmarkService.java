package com.sponus.sponusbe.domain.bookmark.service;

import static com.sponus.sponusbe.domain.announcement.exception.AnnouncementErrorCode.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.announcement.exception.AnnouncementException;
import com.sponus.sponusbe.domain.announcement.repository.AnnouncementRepository;
import com.sponus.sponusbe.domain.bookmark.dto.BookmarkGetResponse;
import com.sponus.sponusbe.domain.bookmark.dto.BookmarkToggleRequest;
import com.sponus.sponusbe.domain.bookmark.dto.BookmarkToggleResponse;
import com.sponus.sponusbe.domain.bookmark.entity.Bookmark;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.bookmark.repository.BookmarkRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BookmarkService {

	private final BookmarkRepository bookmarkRepository;
	private final AnnouncementRepository announcementRepository;

	public BookmarkToggleResponse bookmarkToggle(Organization organization, BookmarkToggleRequest request) {
		Announcement announcement = announcementRepository.findById(request.announcementId())
			.orElseThrow(() -> new AnnouncementException(ANNOUNCEMENT_NOT_FOUND));
		Bookmark existingBookmark = bookmarkRepository.findByOrganizationAndAnnouncement(organization, announcement)
			.orElse(null);

		if (existingBookmark != null) {
			bookmarkRepository.delete(existingBookmark);
			existingBookmark.decreaseSaveCount();
			return BookmarkToggleResponse.from(existingBookmark, false); // 이미 북마크가 되어있는 경우 취소
		} else {
			final Bookmark bookmark = bookmarkRepository.save(request.toEntity(organization, announcement));
			bookmark.increaseSaveCount();
			return BookmarkToggleResponse.from(bookmark, true); // 북마크가 안되어있는 경우 등록
		}
	}

	public List<BookmarkGetResponse> getRecentBookmark(Organization organization) {
		return bookmarkRepository.findByOrganizationOrderByCreatedAtDesc(organization)
			.stream()
			.map(bookmark -> BookmarkGetResponse.from(bookmark.getAnnouncement(), bookmark))
			.collect(Collectors.toList());
	}

	public List<BookmarkGetResponse> getSavedBookmark(Organization organization) {
		List<Bookmark> bookmarks = bookmarkRepository.findByOrganizationOrderBySaveCountDesc(organization);
		return bookmarks.stream()
			.map(bookmark -> BookmarkGetResponse.from(bookmark.getAnnouncement(), bookmark))
			.collect(Collectors.toList());
	}
}
