package com.sponus.sponusbe.domain.propose.dto.request;

import com.sponus.sponusbe.domain.propose.entity.ProposeStatus;

import jakarta.validation.constraints.NotNull;

public record ProposeStatusUpdateRequest(
	@NotNull(message = "[ERROR] 제안 상태 입력은 필수 입니다.")
	ProposeStatus status
) {
}
