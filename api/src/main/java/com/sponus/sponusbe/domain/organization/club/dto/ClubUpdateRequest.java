package com.sponus.sponusbe.domain.organization.club.dto;

import com.sponus.coredomain.domain.organization.enums.ClubType;
import com.sponus.coredomain.domain.organization.enums.ProfileStatus;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClubUpdateRequest(
	@NotBlank(message = "동아리 이름은 필수 입력 값입니다.")
	@Size(min = 1, max = 13, message = "동아리 이름은 1자 이상 13자 이하로 입력해주세요.")
	String name,

	@Max(value = 300, message = "동아리 설명은 300자 이하로 입력해주세요.")
	String description,

	@NotBlank(message = "동아리 이미지 URL은 필수 입력 값입니다.")
	String imageUrl,

	@Min(value = 0, message = "동아리원 수는 0 이상의 값이어야 합니다.")
	int memberCount,

	@NotNull(message = "동아리  타입은 필수 입력 값입니다.")
	ClubType clubType,

	@NotNull(message = "프로필 공개 여부는 필수 입력 값입니다.")
	ProfileStatus profileStatus
) {
}
