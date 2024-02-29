package com.sponus.sponusbe.domain.announcement.dto.response;

import java.time.LocalDateTime;

import com.sponus.coredomain.domain.announcement.Announcement;
import com.sponus.coredomain.domain.announcement.AnnouncementImage;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementCategory;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementStatus;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementType;

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
	Long viewCount,
	LocalDateTime createdAt,
	LocalDateTime updatedAt,
	Long saveCount
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
			.createdAt(announcement.getCreatedAt())
			.updatedAt(announcement.getUpdatedAt())
			.saveCount(announcement.getBookmarkSaveCount())
			.build();
	}

	public AnnouncementStatus getStatus() {
		return status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
