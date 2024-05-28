package com.sponus.sponusbe.domain.bookmark.dto.response;

import java.time.LocalDateTime;

import com.sponus.coredomain.domain.bookmark.Bookmark;
import com.sponus.coredomain.domain.organization.Club;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.sponusbe.domain.organization.club.dto.ClubGetResponse;

import lombok.Builder;

@Builder
public record BookmarkGetResponse(
	Long id,
	Long organizationId,
	Long target,
	String name,
	String imageUrl,
	OrganizationType targetType,
	LocalDateTime createdAt
) {

	public static BookmarkGetResponse from(Bookmark bookmark) {
		return BookmarkGetResponse.builder()
			.id(bookmark.getId())
			.organizationId(bookmark.getOrganization().getId())
			.target(bookmark.getTarget().getId())
			.name(bookmark.getTarget().getName())
			.imageUrl(bookmark.getTarget().getImageUrl())
			.targetType(bookmark.getTarget().getOrganizationType())
			.createdAt(bookmark.getCreatedAt())
			.build();
	}
}

