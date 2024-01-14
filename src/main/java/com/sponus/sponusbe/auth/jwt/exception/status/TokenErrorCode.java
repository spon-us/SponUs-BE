package com.sponus.sponusbe.auth.jwt.exception.status;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.sponus.sponusbe.global.common.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenErrorCode implements BaseErrorCode {

	NO_TOKEN(BAD_REQUEST, "4000", "토큰이 존재하지 않습니다."),
	TOKEN_EXPIRED(BAD_REQUEST, "4001", "만료된 토큰입니다."),
	INVALID_FORM_TOKEN(BAD_REQUEST, "4002", "유효하지 않은 형식의 토큰입니다."),
	INTERNAL_TOKEN_EXCEPTION(BAD_REQUEST, "4003", "토큰 에러입니다."),
	SIGNATURE_ERROR(BAD_REQUEST, "4004", "무결하지 않은 토큰입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
