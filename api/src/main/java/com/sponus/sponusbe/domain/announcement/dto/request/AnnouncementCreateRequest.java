package com.sponus.sponusbe.domain.announcement.dto.request;

import com.sponus.coredomain.domain.announcement.Announcement;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementCategory;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementStatus;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementType;
import com.sponus.coredomain.domain.organization.Organization;

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

	public Announcement toEntity(Organization writer) {
		return Announcement.builder()
			.writer(writer)
			.title(title)
			.type(type)
			.category(category)
			.content(content)
			.status(AnnouncementStatus.OPENED)
			.build();
	}
}

