package com.sponus.sponusbe.domain.announcement.dto.response;

import com.sponus.coredomain.domain.announcement.Announcement;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementCategory;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementStatus;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementType;

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
			.status(announcement.getStatus())
			.viewCount(announcement.getViewCount())
			.build();
	}
}
