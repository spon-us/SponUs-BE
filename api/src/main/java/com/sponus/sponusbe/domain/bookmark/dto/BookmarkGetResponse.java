package com.sponus.sponusbe.domain.bookmark.dto;

import java.time.LocalDateTime;

import com.sponus.coredomain.domain.announcement.Announcement;
import com.sponus.coredomain.domain.announcement.AnnouncementImage;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementCategory;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementType;
import com.sponus.coredomain.domain.bookmark.Bookmark;
import com.sponus.sponusbe.domain.announcement.dto.response.AnnouncementImageResponse;

import lombok.Builder;

@Builder
public record BookmarkGetResponse(
	Long id,
	Long writerId,
	String writerName,
	String title,
	AnnouncementType type,
	AnnouncementCategory category,
	AnnouncementImageResponse mainImage,
	LocalDateTime createdAt,
	Long viewCount,
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
			.writerName(announcement.getWriter().getName())
			.title(announcement.getTitle())
			.type(announcement.getType())
			.category(announcement.getCategory())
			.mainImage(AnnouncementImageResponse.from(mainImage))
			.createdAt(bookmark.getCreatedAt())
			.viewCount(announcement.getViewCount())
			.saveCount(bookmark.getSaveCount())
			.build();
	}
}

