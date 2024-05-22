package com.sponus.sponusbe.domain.portfolio.exception;

import org.springframework.http.HttpStatus;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coredomain.domain.common.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PortfolioErrorCode implements BaseErrorCode {
	PORTFOLIO_NOT_FOUND(HttpStatus.NOT_FOUND, "PFL4040", "존재하지 않는 포트폴리오입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
