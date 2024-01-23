package com.sponus.sponusbe.domain.announcement.dto;

import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementCategory;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnnouncementCreateRequest(
	@NotBlank(message = "[ERROR] 타이틀 입력은 필수 입니다.")
	String title,
	@NotNull(message = "[ERROR] 유형 입력은 필수 입니다.")
	AnnouncementType type,
	@NotNull(message = "[ERROR] 카테코리 입력은 필수 입니다.")
	AnnouncementCategory category,
	@NotBlank(message = "[ERROR] 내용 입력은 필수 입니다.")
	String content
) {
}
