package com.sponus.sponusbe.domain.propose.controller.dto.request;

import com.sponus.sponusbe.domain.propose.entity.ProposeStatus;

public record ProposeUpdateRequest(
	String title,
	String content,
	ProposeStatus status
) {
}
