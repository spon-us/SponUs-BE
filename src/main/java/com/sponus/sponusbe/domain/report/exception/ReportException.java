package com.sponus.sponusbe.domain.report.exception;

import com.sponus.sponusbe.global.common.BaseErrorCode;
import com.sponus.sponusbe.global.common.exception.CustomException;

public class ReportException extends CustomException {

	public ReportException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
