package com.sponus.sponusbe.domain.bookmark.dto;

import com.sponus.coredomain.domain.bookmark.Bookmark;

import lombok.Builder;

@Builder
public record BookmarkToggleResponse(
	Long id,
	Long organizationId,
	Long announcementId,
	Boolean bookmarked
) {

	public static BookmarkToggleResponse from(Bookmark bookmark, boolean bookmarked) {
		return BookmarkToggleResponse.builder()
			.id(bookmark.getId())
			.organizationId(bookmark.getOrganization().getId())
			.announcementId(bookmark.getAnnouncement().getId())
			.bookmarked(bookmarked)
			.build();
	}
}
