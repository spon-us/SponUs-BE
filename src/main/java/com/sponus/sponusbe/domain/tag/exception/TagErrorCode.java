package com.sponus.sponusbe.domain.tag.exception;

import org.springframework.http.HttpStatus;

import com.sponus.sponusbe.global.common.ApiResponse;
import com.sponus.sponusbe.global.common.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TagErrorCode implements BaseErrorCode {
	TAG_ERROR(HttpStatus.BAD_REQUEST, "ORG4000", "태그 관련 에러"),
	INVALID_FORMAT(HttpStatus.BAD_REQUEST, "ORG4001", "잘못된 형식입니다."),
	TAG_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "ORG4002", "중복된 태그입니다."),
	TAG_NOT_FOUND(HttpStatus.NOT_FOUND, "ORG4040", "존재하지 않는 태그입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
