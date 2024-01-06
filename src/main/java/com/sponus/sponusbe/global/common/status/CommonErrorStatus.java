package com.sponus.sponusbe.global.common.status;

import org.springframework.http.HttpStatus;

import com.sponus.sponusbe.global.common.BaseErrorCode;
import com.sponus.sponusbe.global.dto.ErrorReasonDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonErrorStatus implements BaseErrorCode {

	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "서버 에러."),
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "400", "잘못된 요청입니다."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401", "인증이 필요합니다."),
	FORBIDDEN(HttpStatus.FORBIDDEN, "403", "권한이 없습니다."),
	;

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
