package com.sponus.sponusbe.domain.organization.dto.request;

import com.sponus.coredomain.domain.organization.enums.OrganizationType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrganizationCreateRequest(
	@NotBlank(message = "[ERROR] 이메일은 필수 입력 값입니다.")
	@Email(message = "[ERROR] 이메일 형식이 올바르지 않습니다.")
	String email,

	@NotBlank(message = "[ERROR] 비밀번호는 필수 입력 값입니다.")
	String password,

	@NotBlank(message = "[ERROR] 이름은 필수 입력 값입니다.")
	String name,

	@NotNull(message = "[ERROR] 조직 타입은 필수 입력 값입니다.")
	OrganizationType organizationType
) {
}
