package com.sponus.sponusbe.domain.announcement.dto;

import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementCategory;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementStatus;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementType;
import com.sponus.sponusbe.domain.organization.entity.Organization;

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
	String content,

	AnnouncementStatus status
) {

	public Announcement toEntity(Organization writer) {
		return Announcement.builder()
			.writer(writer)
			.title(title)
			.type(type)
			.category(category)
			.content(content)
			.status(status)
			.build();
	}
}
