package com.sponus.sponusbe.domain.propose.exception;

import org.springframework.http.HttpStatus;

import com.sponus.sponusbe.global.common.ApiResponse;
import com.sponus.sponusbe.global.common.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProposeErrorCode implements BaseErrorCode {
	ANNOUNCEMENT_ID_IS_REQUIRED(HttpStatus.BAD_REQUEST, "4001", "공고 ID가 필요합니다."),
	INVALID_ORGANIZATION(HttpStatus.BAD_REQUEST, "4002", "해당 단체의 제안이 아닙니다."),
	PROPOSE_NOT_FOUND(HttpStatus.NOT_FOUND, "4041", "해당 제안이 존재하지 않습니다."),
	INTERNAL_PROPOSE_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "5001", "서버 에러가 발생했습니다. 관리자에게 문의해주세요.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
