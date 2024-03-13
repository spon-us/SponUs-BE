package com.sponus.coreinfraportone;

import com.sponus.coredomain.domain.common.BaseErrorCode;

import lombok.Getter;

@Getter
public class PaymentException extends RuntimeException {

	private final BaseErrorCode errorCode;

	public PaymentException(BaseErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
