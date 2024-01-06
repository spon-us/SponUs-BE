package com.sponus.sponusbe.global.common.status;

public enum SuccessStatus {
	OK("OK", "Operation successful"),
	;

	private final String code;
	private final String message;

	SuccessStatus(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
