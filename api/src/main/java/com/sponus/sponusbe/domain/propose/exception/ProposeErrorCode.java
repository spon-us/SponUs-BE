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
	ANNOUNCEMENT_ID_IS_REQUIRED(HttpStatus.BAD_REQUEST, "PROP4001", "공고 ID가 필요합니다."),
	INVALID_PROPOSING_ORGANIZATION(HttpStatus.BAD_REQUEST, "PROP4002", "해당 단체가 작성한 제안이 아닙니다."),
	INVALID_PROPOSED_ORGANIZATION(HttpStatus.BAD_REQUEST, "PROP4003", "제안을 받은 단체만 접근이 가능합니다."),
	PROPOSE_STATUS_NOT_PENDING(HttpStatus.BAD_REQUEST, "PROP4004", "수락 대기 중인 제안만 수정이 가능합니다."),
	INVALID_PROPOSE_STATUS(HttpStatus.BAD_REQUEST, "PROP4005", "유효하지 않은 제안 상태입니다."),
	PROPOSE_ALREADY_PAID(HttpStatus.BAD_REQUEST, "PROP4006", "이미 결제된 제안입니다."),
	PROPOSE_NOT_FOUND(HttpStatus.NOT_FOUND, "PROP4040", "해당 제안이 존재하지 않습니다."),
	PAYMENT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "PROP5001", "결제 중 에러가 발생했습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
