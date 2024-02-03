package com.sponus.sponusbe.domain.organization.service;

import static com.sponus.sponusbe.domain.announcement.exception.AnnouncementErrorCode.*;

import org.springframework.stereotype.Service;

import com.sponus.sponusbe.domain.announcement.dto.request.AnnouncementCreateRequest;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementCreateResponse;
import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.announcement.exception.AnnouncementException;
import com.sponus.sponusbe.domain.announcement.repository.AnnouncementRepository;
import com.sponus.sponusbe.domain.organization.dto.BookmarkToggleRequest;
import com.sponus.sponusbe.domain.organization.dto.BookmarkToggleResponse;
import com.sponus.sponusbe.domain.organization.entity.Bookmark;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.organization.repository.BookmarkRepository;
import com.sponus.sponusbe.domain.organization.repository.OrganizationRepository;

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
			return BookmarkToggleResponse.from(existingBookmark, false); // 이미 북마크가 되어있는 경우 취소
		} else {
			final Bookmark bookmark = bookmarkRepository.save(request.toEntity(organization, announcement));
			return BookmarkToggleResponse.from(bookmark, true); // 북마크가 안되어있는 경우 등록
		}
	}
}
