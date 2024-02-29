package com.sponus.sponusbe.domain.announcement.dto.response;

import jakarta.validation.constraints.NotBlank;

public record AnnouncementStatusUpdateResponse(
	@NotBlank(message = "[ERROR] 공고 상태 입력은 필수 입니다.")
	String status
) {
}
