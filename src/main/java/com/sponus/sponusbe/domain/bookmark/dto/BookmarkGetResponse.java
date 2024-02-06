package com.sponus.sponusbe.domain.bookmark.dto;

import java.time.LocalDateTime;

import com.sponus.sponusbe.domain.bookmark.entity.Bookmark;

import lombok.Builder;

// TODO: 이미지 등 추가
@Builder
public record BookmarkGetResponse(
	Long id,
	Long announcementId,
	String announcementTitle,
	LocalDateTime createdAt,
	Long saveCount
) {

	public static BookmarkGetResponse from(Bookmark bookmark) {
		return BookmarkGetResponse.builder()
			.id(bookmark.getId())
			.announcementId(bookmark.getAnnouncement().getId())
			.announcementTitle(bookmark.getAnnouncement().getTitle())
			.createdAt(bookmark.getCreatedAt())
			.saveCount(bookmark.getSaveCount())
			.build();
	}
}

