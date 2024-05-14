package com.sponus.sponusbe.domain.bookmark.service;

import static com.sponus.sponusbe.domain.announcement.exception.AnnouncementErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.announcement.Announcement;
import com.sponus.coredomain.domain.announcement.repository.AnnouncementRepository;
import com.sponus.coredomain.domain.bookmark.Bookmark;
import com.sponus.coredomain.domain.bookmark.repository.BookmarkRepository;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.sponusbe.domain.announcement.exception.AnnouncementException;
import com.sponus.sponusbe.domain.bookmark.dto.BookmarkToggleRequest;
import com.sponus.sponusbe.domain.bookmark.dto.BookmarkToggleResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
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
}
