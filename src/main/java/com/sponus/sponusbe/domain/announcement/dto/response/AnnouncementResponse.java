package com.sponus.sponusbe.domain.announcement.dto.response;

import java.util.List;

import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementCategory;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementStatus;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementType;

import lombok.Builder;

@Builder
public record AnnouncementResponse(
	Long id,
	Long writerId,
	String title,
	AnnouncementType type,
	AnnouncementCategory category,
	String content,
	List<AnnouncementImageResponse> announcementImages,
	AnnouncementStatus status,
	Long viewCount
) {
	public static AnnouncementResponse from(Announcement announcement) {
		List<AnnouncementImageResponse> announcementImages = announcement.getAnnouncementImages()
			.stream()
			.map(AnnouncementImageResponse::from)
			.toList();

		return AnnouncementResponse.builder()
			.id(announcement.getId())
			.writerId(announcement.getWriter().getId())
			.title(announcement.getTitle())
			.type(announcement.getType())
			.category(announcement.getCategory())
			.content(announcement.getContent())
			.announcementImages(announcementImages)
			.status(announcement.getStatus())
			.viewCount(announcement.getViewCount())
			.build();
	}
}
