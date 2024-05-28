package com.sponus.sponusbe.domain.bookmark.dto.response;

import com.sponus.coredomain.domain.bookmark.Bookmark;

import lombok.Builder;

@Builder
public record BookmarkToggleResponse(
	Long id,
	Long organizationId,
	Long target,
	Boolean bookmarked
) {

	public static BookmarkToggleResponse from(Bookmark bookmark, boolean bookmarked) {
		return BookmarkToggleResponse.builder()
			.id(bookmark.getId())
			.organizationId(bookmark.getOrganization().getId())
			.target(bookmark.getTarget().getId())
			.bookmarked(bookmarked)
			.build();
	}
}
