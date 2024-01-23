package com.sponus.sponusbe.global.common.code;

import org.springframework.http.HttpStatus;

import com.sponus.sponusbe.global.common.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportErrorCode implements BaseErrorCode {
	REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "REPORT4001", "보고서가 없습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
