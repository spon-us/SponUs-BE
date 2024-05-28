package com.sponus.sponusbe.domain.bookmark.dto.response;

import java.time.LocalDateTime;

import com.sponus.coredomain.domain.bookmark.Bookmark;

import lombok.Builder;

@Builder
public record BookmarkGetResponse(
	Long id,
	Long writerId,
	String writerName,
	String title,
	LocalDateTime createdAt,
	Long viewCount,
	Long saveCount
) {

	public static BookmarkGetResponse from(Bookmark bookmark) {
		return null;
	}
}

