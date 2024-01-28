package com.sponus.sponusbe.domain.announcement.dto.response;

import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementCategory;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementStatus;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementType;

import lombok.Builder;

@Builder
public record AnnouncementCreateResponse(
	Long id,
	Long writerId,
	String title,
	AnnouncementType type,
	AnnouncementCategory category,
	String content,
	AnnouncementStatus status,
	Long viewCount
) {
	public static AnnouncementCreateResponse from(Announcement announcement) {
		return AnnouncementCreateResponse.builder()
			.id(announcement.getId())
			.writerId(announcement.getWriter().getId())
			.title(announcement.getTitle())
			.type(announcement.getType())
			.category(announcement.getCategory())
			.content(announcement.getContent())
			.status(AnnouncementStatus.POSTED)
			.viewCount(announcement.getViewCount())
			.build();
	}
}