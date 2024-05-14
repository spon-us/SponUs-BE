package com.sponus.sponusbe.domain.propose.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ProposeStatusUpdateRequest(
	@NotBlank(message = "[ERROR] 제안 상태 입력은 필수 입니다.")
	String status
) {
}
