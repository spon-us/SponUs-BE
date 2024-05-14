package com.sponus.sponusbe.domain.bookmark.dto;

import com.sponus.coredomain.domain.bookmark.Bookmark;
import com.sponus.coredomain.domain.organization.Organization;

public record BookmarkToggleRequest(
	Long announcementId
) {

	public Bookmark toEntity(Organization organization) {
		return null;
	}
}

