package com.sponus.sponusbe.domain.organization.exception;

import org.springframework.http.HttpStatus;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coredomain.domain.common.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClubErrorCode implements BaseErrorCode {
	CLUB_ERROR(HttpStatus.BAD_REQUEST, "CLB4000", "동아리 관련 에러"),
	INVALID_FORMAT(HttpStatus.BAD_REQUEST, "CLB4001", "잘못된 형식입니다."),
	CLUB_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "CLB4002", "중복된 동아리 이메일입니다."),
	CLUB_NOT_FOUND(HttpStatus.NOT_FOUND, "CLB4040", "존재하지 않는 동아리입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
