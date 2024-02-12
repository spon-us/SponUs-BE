package com.sponus.sponusbe.domain.announcement.dto.response;

import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementStatus;

import jakarta.validation.constraints.NotNull;

public record AnnouncementStatusUpdateResponse(
	@NotNull(message = "[ERROR] 공고 상태 입력은 필수 입니다.")
	AnnouncementStatus status
) {
}
