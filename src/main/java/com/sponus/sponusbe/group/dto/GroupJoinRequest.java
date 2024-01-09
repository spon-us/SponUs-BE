package com.sponus.sponusbe.group.dto;

import com.sponus.sponusbe.group.entity.Group;
import com.sponus.sponusbe.group.enums.GroupType;
import com.sponus.sponusbe.group.enums.SubGroupType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record GroupJoinRequest(
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

	@NotEmpty(message = "[ERROR] 그룹 유형 입력은 필수입니다.")
	GroupType groupType,

	@NotEmpty(message = "[ERROR] 그룹 서브 유형 입력은 필수입니다.")
	SubGroupType subGroupType,

	@NotEmpty(message = "[ERROR] 담당자 이름 입력은 필수입니다.")
	String managerName,

	@NotEmpty(message = "[ERROR] 담당자 직책 입력은 필수입니다.")
	String managerPosition,

	@NotEmpty(message = "[ERROR] 담당자 이메일 입력은 필수입니다.")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "[ERROR] 이메일 형식에 맞지 않습니다.")
	String managerEmail,

	@NotEmpty(message = "[ERROR] 담당자 전화번호 입력은 필수입니다.")
	String managerPhone
) {

	public static Group toGroupEntity(
		GroupJoinRequest request,
		String encodedPassword
	) {
		return Group.builder()
			.name(request.name())
			.email(request.email())
			.password(encodedPassword)
			.location(request.location)
			.groupType(request.groupType)
			.subGroupType(request.subGroupType)
			.managerName(request.managerName)
			.managerPosition(request.managerPosition)
			.managerEmail(request.managerEmail)
			.managerPhone(request.managerPhone)
			.build();
	}
}

