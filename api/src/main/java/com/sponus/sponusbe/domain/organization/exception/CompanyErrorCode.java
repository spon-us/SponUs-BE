package com.sponus.sponusbe.domain.organization.exception;

import org.springframework.http.HttpStatus;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coredomain.domain.common.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CompanyErrorCode implements BaseErrorCode {
	COMPANY_ERROR(HttpStatus.BAD_REQUEST, "CMP4000", "회사 관련 에러"),
	INVALID_FORMAT(HttpStatus.BAD_REQUEST, "CMP4001", "잘못된 형식입니다."),
	COMPANY_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "CMP4002", "중복된 회사 이메일입니다."),
	COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "CMP4040", "존재하지 않는 회사입니다.");
	
	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
