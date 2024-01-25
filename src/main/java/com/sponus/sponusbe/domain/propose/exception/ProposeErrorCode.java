package com.sponus.sponusbe.domain.propose.exception;

import org.springframework.http.HttpStatus;

import com.sponus.sponusbe.global.common.ApiResponse;
import com.sponus.sponusbe.global.common.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProposeErrorCode implements BaseErrorCode {
	PROPOSE_ERROR(HttpStatus.BAD_REQUEST, "PROP4000", "제안 관련 에러"),
	ANNOUNCEMENT_ID_IS_REQUIRED(HttpStatus.BAD_REQUEST, "PROP4001", "공고 ID가 필요합니다."),
	INVALID_ORGANIZATION(HttpStatus.BAD_REQUEST, "PROP4002", "해당 단체의 제안이 아닙니다."),
	PROPOSE_NOT_FOUND(HttpStatus.NOT_FOUND, "PROP4040", "해당 제안이 존재하지 않습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
