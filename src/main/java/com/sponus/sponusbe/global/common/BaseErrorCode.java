package com.sponus.sponusbe.global.common;

import com.sponus.sponusbe.global.dto.ErrorReasonDTO;

public interface BaseErrorCode {

	public ErrorReasonDTO getReason();

	public ErrorReasonDTO getReasonHttpStatus();
}
