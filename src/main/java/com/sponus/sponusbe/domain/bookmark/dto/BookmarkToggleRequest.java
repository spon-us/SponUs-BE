package com.sponus.sponusbe.domain.bookmark.dto;

import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.bookmark.entity.Bookmark;
import com.sponus.sponusbe.domain.organization.entity.Organization;

public record BookmarkToggleRequest(
	Long announcementId
) {

	public Bookmark toEntity(Organization organization, Announcement announcement) {
		return Bookmark.builder()
			.organization(organization)
			.announcement(announcement)
			.build();
	}
}

