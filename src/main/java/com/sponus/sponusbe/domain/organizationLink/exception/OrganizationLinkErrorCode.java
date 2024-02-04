package com.sponus.sponusbe.domain.organizationLink.exception;

import org.springframework.http.HttpStatus;

import com.sponus.sponusbe.global.common.ApiResponse;
import com.sponus.sponusbe.global.common.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrganizationLinkErrorCode implements BaseErrorCode {
	ORGANIZATION_ERROR(HttpStatus.BAD_REQUEST, "ORGLK4000", "단체 관련 에러"),
	INVALID_FORMAT(HttpStatus.BAD_REQUEST, "ORGLK4001", "잘못된 형식입니다."),
	ORGANIZATION_LINK_NOT_FOUND(HttpStatus.NOT_FOUND, "ORGLK4040", "존재하지 않는 조직 링크입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
