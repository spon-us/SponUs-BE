package com.sponus.sponusbe.domain.bookmark.dto;

import java.time.LocalDateTime;

import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementImageResponse;
import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.announcement.entity.AnnouncementImage;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementCategory;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementType;
import com.sponus.sponusbe.domain.bookmark.entity.Bookmark;

import lombok.Builder;

@Builder
public record BookmarkGetResponse(
	Long id,
	Long writerId,
	String title,
	AnnouncementType type,
	AnnouncementCategory category,
	AnnouncementImageResponse mainImage,
	LocalDateTime createdAt,
	Long saveCount
) {

	public static BookmarkGetResponse from(Announcement announcement, Bookmark bookmark) {
		AnnouncementImage mainImage = announcement.getAnnouncementImages()
			.stream()
			.findFirst()
			.orElseThrow();

		return BookmarkGetResponse.builder()
			.id(announcement.getId())
			.writerId(announcement.getWriter().getId())
			.title(announcement.getTitle())
			.type(announcement.getType())
			.category(announcement.getCategory())
			.mainImage(AnnouncementImageResponse.from(mainImage))
			.createdAt(bookmark.getCreatedAt())
			.saveCount(bookmark.getSaveCount())
			.build();
	}
}

