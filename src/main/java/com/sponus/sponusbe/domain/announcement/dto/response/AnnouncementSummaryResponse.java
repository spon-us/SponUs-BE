package com.sponus.sponusbe.domain.announcement.dto.response;

import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.announcement.entity.AnnouncementImage;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementCategory;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementStatus;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementType;

import lombok.Builder;

@Builder
public record AnnouncementSummaryResponse(
	Long id,
	Long writerId,
	String writerName,
	String title,
	AnnouncementType type,
	AnnouncementCategory category,
	AnnouncementImageResponse mainImage,
	AnnouncementStatus status,
	Long viewCount
) {
	public static AnnouncementSummaryResponse from(Announcement announcement) {
		AnnouncementImage mainImage = announcement.getAnnouncementImages()
			.stream()
			.findFirst().orElseThrow();

		return AnnouncementSummaryResponse.builder()
			.id(announcement.getId())
			.writerId(announcement.getWriter().getId())
			.writerName(announcement.getWriter().getName())
			.title(announcement.getTitle())
			.type(announcement.getType())
			.category(announcement.getCategory())
			.mainImage(AnnouncementImageResponse.from(mainImage))
			.status(announcement.getStatus())
			.viewCount(announcement.getViewCount())
			.build();
	}
}
