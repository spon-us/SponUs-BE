package com.sponus.sponusbe.domain.report.exception;

import com.sponus.coredomain.domain.common.BaseErrorCode;
import com.sponus.sponusbe.global.exception.CustomException;

public class ReportException extends CustomException {

	public ReportException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
