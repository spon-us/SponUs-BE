package com.sponus.sponusbe.domain.announcement.dto.request;

import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementCategory;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementStatus;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementType;

import lombok.Builder;

@Builder
public record AnnouncementUpdateRequest(
	String title,
	AnnouncementType type,
	AnnouncementCategory category,
	String content,
	AnnouncementStatus status
) {
}
