package com.sponus.sponusbe.domain.bookmark.dto.request;

import com.sponus.coredomain.domain.bookmark.Bookmark;
import com.sponus.coredomain.domain.organization.Organization;

import jakarta.validation.constraints.NotNull;

public record BookmarkToggleRequest(
	@NotNull
	Long target
) {

	public Bookmark toEntity(Organization organization, Organization target) {
		return Bookmark.builder()
			.organization(organization)
			.target(target)
			.build();
	}
}

