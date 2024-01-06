package com.sponus.sponusbe.global.dto;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorReasonDTO {

	private HttpStatus httpStatus;

	private final boolean status;
	private final String code;
	private final String message;

	public boolean getStatus() {
		return status;
	}
}
