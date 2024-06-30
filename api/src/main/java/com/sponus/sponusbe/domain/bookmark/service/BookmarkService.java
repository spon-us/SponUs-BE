package com.sponus.sponusbe.domain.bookmark.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.bookmark.Bookmark;
import com.sponus.coredomain.domain.bookmark.repository.BookmarkRepository;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.repository.OrganizationRepository;
import com.sponus.sponusbe.domain.bookmark.dto.request.BookmarkToggleRequest;
import com.sponus.sponusbe.domain.bookmark.dto.response.BookmarkToggleResponse;
import com.sponus.sponusbe.domain.bookmark.exception.BookmarkErrorCode;
import com.sponus.sponusbe.domain.bookmark.exception.BookmarkException;
import com.sponus.sponusbe.domain.organization.exception.OrganizationErrorCode;
import com.sponus.sponusbe.domain.organization.exception.OrganizationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class BookmarkService {

	private final BookmarkRepository bookmarkRepository;
	private final OrganizationRepository organizationRepository;

	public BookmarkToggleResponse bookmarkToggle(Organization organization, BookmarkToggleRequest request) {

		if (organization.getId().equals(request.target()))
			throw new BookmarkException(BookmarkErrorCode.BOOKMARK_ERROR);

		final Organization target = organizationRepository.findById(request.target())
			.orElseThrow(() -> new OrganizationException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND));
		return bookmarkRepository.findByOrganizationAndTarget(organization, target)
			.map(existingBookmark -> {
				bookmarkRepository.delete(existingBookmark);
				return BookmarkToggleResponse.from(existingBookmark, false);
			})
			.orElseGet(() -> {
				final Bookmark bookmark = bookmarkRepository.save(request.toEntity(organization, target));
				return BookmarkToggleResponse.from(bookmark, true);
			});
	}
}
