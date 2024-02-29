package com.sponus.sponusbe.domain.report.exception;

import org.springframework.http.HttpStatus;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coredomain.domain.common.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportErrorCode implements BaseErrorCode {
	INVALID_ORGANIZATION(HttpStatus.BAD_REQUEST, "ANC4002", "해당 단체의 보고서가 아닙니다."),
	REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "REPORT4040", "보고서가 없습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
