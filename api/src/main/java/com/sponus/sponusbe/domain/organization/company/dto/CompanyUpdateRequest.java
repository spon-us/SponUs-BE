package com.sponus.sponusbe.domain.organization.company.dto;

import com.sponus.coredomain.domain.organization.enums.CollaborationType;
import com.sponus.coredomain.domain.organization.enums.CompanyType;
import com.sponus.coredomain.domain.organization.enums.ProfileStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CompanyUpdateRequest(
	@NotBlank(message = "[ERROR] 회사 이름은 필수 입력 값입니다.")
	@Size(min = 1, max = 13, message = "[ERROR] 회사 이름은 1자 이상 13자 이하로 입력해주세요.")
	String name,

	@Size(max = 300, message = "[ERROR] 회사 설명은 300자 이하로 입력해주세요.")
	String description,

	@NotBlank(message = "[ERROR] 회사 이미지 URL은 필수 입력 값입니다.")
	String imageUrl,

	@NotNull(message = "[ERROR] 회사 타입은 필수 입력 값입니다.")
	CompanyType companyType,

	@NotNull(message = "[ERROR] 협업 타입은 필수 입력 값입니다.")
	CollaborationType collaborationType,

	String sponsorshipContent, // 협찬 물품

	@NotNull(message = "[ERROR] 프로필 공개 여부는 필수 입력 값입니다.")
	ProfileStatus profileStatus
) {
}
