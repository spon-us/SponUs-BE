package com.sponus.sponusbe.domain.organization.company.dto;

import com.sponus.coredomain.domain.organization.enums.CollaborationType;
import com.sponus.coredomain.domain.organization.enums.CompanyType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompanyUpdateRequest(
	String name,

	@Email(message = "이메일 형식이 아닙니다.")
	String email,

	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	String password,

	String description,

	String imageUrl,

	@NotNull(message = "회사 타입은 필수 입력 값입니다.")
	CompanyType companyType,

	@NotNull(message = "협업 타입은 필수 입력 값입니다.")
	CollaborationType collaborationType,

	String sponsorshipContent
) {
}
