package com.sponus.sponusbe.global.common.code;

import org.springframework.http.HttpStatus;

import com.sponus.sponusbe.global.common.ApiResponse;
import com.sponus.sponusbe.global.common.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrganizationErrorCode implements BaseErrorCode {
	ORGANIZATION_ERROR(HttpStatus.BAD_REQUEST, "4000", "단체 관련 에러"),

	ORGANIZATION_EMAIL_DUPLICATE(HttpStatus.BAD_REQUEST, "4001", "중복된 이메일이 존재합니다."),
	INVALID_FORMAT(HttpStatus.BAD_REQUEST, "4002", "잘못된 형식입니다."),

	ORGANIZATION_NOT_FOUND(HttpStatus.NOT_FOUND, "4004", "없는 회원입니다."),
	ORGANIZATION_NO_AUTHENTICATED(HttpStatus.BAD_REQUEST, "4005", "인증이 필요합니다."),
	ORGANIZATION_NO_PERMISSION(HttpStatus.FORBIDDEN, "4006", "권한이 없습니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "5000", "서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
