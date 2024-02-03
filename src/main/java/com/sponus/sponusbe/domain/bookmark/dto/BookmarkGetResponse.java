package com.sponus.sponusbe.domain.bookmark.dto;

import static com.sponus.sponusbe.domain.announcement.entity.QAnnouncement.*;

import java.time.LocalDateTime;

import com.sponus.sponusbe.domain.bookmark.entity.Bookmark;

import lombok.Builder;

// TODO: 이미지 등등 추가
@Builder
public record BookmarkGetResponse(
	Long id,
	String announcementTitle,
	LocalDateTime createdAt
) {

	public static BookmarkGetResponse from(Bookmark bookmark) {
		return BookmarkGetResponse.builder()
			.id(bookmark.getId())
			.announcementTitle(bookmark.getAnnouncement().getTitle())
			.createdAt(bookmark.getLastSavedAt())
			.build();
	}
}

