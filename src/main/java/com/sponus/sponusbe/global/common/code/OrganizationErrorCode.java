package com.sponus.sponusbe.global.common.code;

import org.springframework.http.HttpStatus;

import com.sponus.sponusbe.global.common.BaseErrorCode;
import com.sponus.sponusbe.global.dto.ErrorReasonDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrganizationErrorCode implements BaseErrorCode {
	ORGANIZATION_ERROR(HttpStatus.BAD_REQUEST, "4000", "단체 관련 에러"),

	ORGANIZATION_EMAIL_DUPLICATE(HttpStatus.BAD_REQUEST, "4001", "중복된 이메일이 존재합니다."),
	INVALID_FORMAT(HttpStatus.BAD_REQUEST, "4002", "잘못된 형식입니다."),

	RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "4004", "없는 회원입니다."),
	ORGANIZATION_NO_AUTHENTICATED(HttpStatus.BAD_REQUEST, "4005", "인증이 필요합니다."),
	ORGANIZATION_NO_PERMISSION(HttpStatus.FORBIDDEN, "4006", "권한이 없습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ErrorReasonDTO getReason() {
		return ErrorReasonDTO.builder()
			.message(message)
			.code(code)
			.status(false)
			.build();
	}

	@Override
	public ErrorReasonDTO getReasonHttpStatus() {
		return ErrorReasonDTO.builder()
			.message(message)
			.code(code)
			.status(false)
			.httpStatus(httpStatus)
			.build()
			;
	}
}
