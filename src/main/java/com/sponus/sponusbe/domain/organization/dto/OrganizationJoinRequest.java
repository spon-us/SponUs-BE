package com.sponus.sponusbe.domain.organization.dto;

import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.organization.entity.enums.OrganizationStatus;
import com.sponus.sponusbe.domain.organization.entity.enums.OrganizationType;
import com.sponus.sponusbe.domain.organization.entity.enums.SuborganizationType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record OrganizationJoinRequest(
	@NotBlank(message = "[ERROR] 이름 입력은 필수 입니다.")
	String name,

	@NotBlank(message = "[ERROR] 이메일 입력은 필수입니다.")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "[ERROR] 이메일 형식에 맞지 않습니다.")
	String email,

	@NotBlank(message = "[ERROR] 비밀번호 입력은 필수 입니다.")
	@Size(min = 10, message = "[ERROR] 비밀번호는 최소 10자리 이이어야 합니다.")
	String password,

	@NotNull(message = "[ERROR] 단체 유형 입력은 필수입니다.")
	OrganizationType organizationType,

	SuborganizationType suborganizationType
) {

	public Organization toEntity(String encodedPassword, String imageUrl) {
		return Organization.builder()
			.name(name)
			.email(email)
			.password(encodedPassword)
			.imageUrl(imageUrl)
			.organizationType(organizationType)
			.suborganizationType(suborganizationType)
			.organizationStatus(OrganizationStatus.ACTIVE)
			.build();
	}
}

