package com.sponus.sponusbe.member.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sponus.sponusbe.member.entity.Member;
import com.sponus.sponusbe.member.enums.Role;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberJoinRequest(
	@NotEmpty(message = "[ERROR] 이메일 입력은 필수입니다.")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "[ERROR] 이메일 형식에 맞지 않습니다.")
	String email,

	@NotEmpty(message = "[ERROR] 이름 입력은 필수 입니다.")
	String name,

	@NotEmpty(message = "[ERROR] 비밀번호 입력은 필수 입니다.")
	@Size(min = 10, message = "[ERROR] 비밀번호는 최소 10자리 이이어야 합니다.")
	String password
) {

	public static Member toMemberEntity(
		MemberJoinRequest request,
		BCryptPasswordEncoder bCryptPasswordEncoder
	) {
		return Member.builder()
			.email(request.email())
			.name(request.name())
			.password(bCryptPasswordEncoder.encode(request.password()))
			.role(Role.MEMBER) // 기본 회원가입 학생
			.build();
	}
}

