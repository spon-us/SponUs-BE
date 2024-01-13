package com.sponus.sponusbe.domain.organization.dto;

import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.organization.entity.enums.OrganizationStatus;
import com.sponus.sponusbe.domain.organization.entity.enums.OrganizationType;
import com.sponus.sponusbe.domain.organization.entity.enums.SuborganizationType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record OrganizationJoinRequest(
	@NotEmpty(message = "[ERROR] 이름 입력은 필수 입니다.")
	String name,

	@NotEmpty(message = "[ERROR] 이메일 입력은 필수입니다.")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "[ERROR] 이메일 형식에 맞지 않습니다.")
	String email,

	@NotEmpty(message = "[ERROR] 비밀번호 입력은 필수 입니다.")
	@Size(min = 10, message = "[ERROR] 비밀번호는 최소 10자리 이이어야 합니다.")
	String password,

	@NotEmpty(message = "[ERROR] 위치 입력은 필수 입니다.")
	String location,

	@NotEmpty(message = "[ERROR] 단체 유형 입력은 필수입니다.")
	OrganizationType organizationType,

	@NotEmpty(message = "[ERROR] 단체 서브 유형 입력은 필수입니다.")
	SuborganizationType suborganizationType,

	@NotEmpty(message = "[ERROR] 담당자 이름 입력은 필수입니다.")
	String managerName,

	@NotEmpty(message = "[ERROR] 담당자 직책 입력은 필수입니다.")
	String managerPosition,

	@NotEmpty(message = "[ERROR] 담당자 이메일 입력은 필수입니다.")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "[ERROR] 이메일 형식에 맞지 않습니다.")
	String managerEmail,

	@NotEmpty(message = "[ERROR] 담당자 전화번호 입력은 필수입니다.")
	String managerPhone,

	@NotEmpty(message = "[ERROR] 담당자 연락 가능 요일 입력은 필수입니다.")
	String managerAvailableDay,

	@NotEmpty(message = "[ERROR] 담당자 연락 가능 시간 입력은 필수입니다.")
	String managerAvailableHour,

	@NotEmpty(message = "[ERROR] 담당자 연락 선호 방법 입력은 필수입니다.")
	String managerContactPreference
) {

	public Organization toEntity(String encodedPassword) {
		return Organization.builder()
			.name(name)
			.email(email)
			.password(encodedPassword)
			.location(location)
			.organizationType(organizationType)
			.suborganizationType(suborganizationType)
			.managerName(managerName)
			.managerPosition(managerPosition)
			.managerEmail(managerEmail)
			.managerPhone(managerPhone)
			.managerAvailableDay(managerAvailableDay)
			.managerAvailableHour(managerAvailableHour)
			.managerContactPreference(managerContactPreference)
			.organizationStatus(OrganizationStatus.ACTIVE)
			.build();
	}
}

