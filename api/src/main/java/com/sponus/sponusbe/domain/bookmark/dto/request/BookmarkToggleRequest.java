package com.sponus.sponusbe.domain.bookmark.dto.request;

import com.sponus.coredomain.domain.bookmark.Bookmark;
import com.sponus.coredomain.domain.organization.Organization;

public record BookmarkToggleRequest(
	Long target
) {

	public Bookmark toEntity(Organization organization, Organization target) {
		return Bookmark.builder()
			.organization(organization)
			.target(target)
			.build();
	}
}

