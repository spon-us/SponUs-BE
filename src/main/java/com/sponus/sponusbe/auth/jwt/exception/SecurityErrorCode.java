package com.sponus.sponusbe.auth.jwt.exception;

import org.springframework.http.HttpStatus;

import com.sponus.sponusbe.global.common.ApiResponse;
import com.sponus.sponusbe.global.common.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SecurityErrorCode implements BaseErrorCode {

	INVALID_TOKEN(HttpStatus.BAD_REQUEST, "4001", "유효하지 않은 형식의 토큰입니다."),
	TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "4011", "토큰 처리 중 예외가 발생했습니다."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "4012", "권한이 없습니다."),
	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "4013", "만료된 토큰입니다."),
	SIGNATURE_ERROR(HttpStatus.UNAUTHORIZED, "4014", "무결하지 않은 토큰입니다."),
	TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "4015", "토큰이 존재하지 않습니다."),
	INTERNAL_SECURITY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "5001", "서버 에러가 발생했습니다. 관리자에게 문의해주세요.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
