package com.sponus.sponusbe.domain.announcement.dto.request;

import com.sponus.coredomain.domain.announcement.enums.AnnouncementCategory;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementType;

import lombok.Builder;

@Builder
public record AnnouncementUpdateRequest(
	String title,
	AnnouncementType type,
	AnnouncementCategory category,
	String content
) {
}
