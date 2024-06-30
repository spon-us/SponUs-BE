package com.sponus.sponusbe.domain.organization.dto.request;

import jakarta.validation.constraints.NotBlank;

public record OrganizationSearchRequest(
	@NotBlank(message = "[ERROR] 검색어 입력은 필수 입니다.")
	String keyword
) {
}
