package com.sponus.sponusbe.domain.bookmark.dto.response;

import java.time.LocalDateTime;

import com.sponus.coredomain.domain.bookmark.Bookmark;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;

import lombok.Builder;

@Builder
public record BookmarkToggleResponse(
	Long id,
	Long organizationId,
	Long target,
	OrganizationType targetType,
	LocalDateTime createdAt,
	Boolean bookmarked
) {

	public static BookmarkToggleResponse from(Bookmark bookmark, boolean bookmarked) {
		return BookmarkToggleResponse.builder()
			.id(bookmark.getId())
			.organizationId(bookmark.getOrganization().getId())
			.target(bookmark.getTarget().getId())
			.targetType(bookmark.getTarget().getOrganizationType())
			.createdAt(bookmark.getCreatedAt())
			.bookmarked(bookmarked)
			.build();
	}
}
