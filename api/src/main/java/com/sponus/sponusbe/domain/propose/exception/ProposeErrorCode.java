package com.sponus.sponusbe.domain.propose.exception;

import org.springframework.http.HttpStatus;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coredomain.domain.common.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProposeErrorCode implements BaseErrorCode {
	PROPOSE_ERROR(HttpStatus.BAD_REQUEST, "PROP4000", "제안 관련 에러"),
	INVALID_PROPOSE_STATUS(HttpStatus.BAD_REQUEST, "PROP4005", "유효하지 않은 제안 상태입니다."),
	PROPOSE_LIMIT_ERROR(HttpStatus.CONFLICT, "PROP4009", "하루 제안 5회 초과하였습니다."),
	PROPOSE_NOT_FOUND(HttpStatus.NOT_FOUND, "PROP4040", "해당 제안이 존재하지 않습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
