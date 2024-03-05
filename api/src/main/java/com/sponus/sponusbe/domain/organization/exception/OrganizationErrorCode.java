package com.sponus.sponusbe.domain.organization.exception;

import org.springframework.http.HttpStatus;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coredomain.domain.common.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrganizationErrorCode implements BaseErrorCode {
	ORGANIZATION_ERROR(HttpStatus.BAD_REQUEST, "ORG4000", "단체 관련 에러"),
	INVALID_FORMAT(HttpStatus.BAD_REQUEST, "ORG4001", "잘못된 형식입니다."),
	ORGANIZATION_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "ORG4002", "중복된 단체 이메일입니다."),
	ORGANIZATION_NOT_FOUND(HttpStatus.NOT_FOUND, "ORG4040", "존재하지 않는 단체입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
