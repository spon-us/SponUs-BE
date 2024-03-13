package com.sponus.coreinfraportone;

import org.springframework.http.HttpStatus;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coredomain.domain.common.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentErrorCode implements BaseErrorCode {
	PAYMENT_ERROR(HttpStatus.BAD_REQUEST, "PAY4000", "결제 중 에러가 발생했습니다."),

	PROPOSE_ALREADY_PAID(HttpStatus.BAD_REQUEST, "PAY4001", "이미 결제된 제안입니다."),

	PROPOSE_NOT_FOUND(HttpStatus.NOT_FOUND, "PAY4002", "해당 제안이 존재하지 않습니다."),
	INVALID_PROPOSED_ORGANIZATION(HttpStatus.BAD_REQUEST, "PAY4003", "제안을 받은 단체만 접근이 가능합니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
