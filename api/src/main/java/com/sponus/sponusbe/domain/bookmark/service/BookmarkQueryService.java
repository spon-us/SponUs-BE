package com.sponus.sponusbe.domain.bookmark.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.bookmark.Bookmark;
import com.sponus.coredomain.domain.bookmark.repository.BookmarkRepository;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.sponusbe.domain.bookmark.dto.response.BookmarkGetResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BookmarkQueryService {

	private final BookmarkRepository bookmarkRepository;

	public List<BookmarkGetResponse> getRecentBookmark(Organization organization) {
		return bookmarkRepository.findByOrganizationOrderByCreatedAtDesc(organization)
			.stream()
			.map(BookmarkGetResponse::from)
			.toList();
	}

	public List<BookmarkGetResponse> getCompanyBookmark(Organization organization) {
		return bookmarkRepository.findByOrganization(organization)
			.stream()
			.filter(bookmark -> bookmark.getTarget().getOrganizationType().equals(OrganizationType.COMPANY))
			.map(BookmarkGetResponse::from)
			.toList();
	}

	public List<BookmarkGetResponse> getClubBookmark(Organization organization) {
		return bookmarkRepository.findByOrganization(organization)
			.stream()
			.filter(bookmark -> bookmark.getTarget().getOrganizationType().equals(OrganizationType.CLUB))
			.map(BookmarkGetResponse::from)
			.toList();
	}
}
