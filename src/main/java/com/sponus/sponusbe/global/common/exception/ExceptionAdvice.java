package com.sponus.sponusbe.global.common.exception;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sponus.sponusbe.global.common.ApiResponse;
import com.sponus.sponusbe.global.common.BaseErrorCode;
import com.sponus.sponusbe.global.common.code.OrganizationErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler({Exception.class})
	public ResponseEntity<ApiResponse<Void>> handleAllException(Exception e) {
		log.error(">>>>> Internal Server Error : ", e);
		BaseErrorCode errorCode = OrganizationErrorCode.INTERNAL_SERVER_ERROR;
		return ResponseEntity.internalServerError().body(
			ApiResponse.onFailure(
				errorCode.getCode(),
				errorCode.getMessage()
			)
		);
	}

	@ExceptionHandler({CustomException.class})
	public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
		log.warn(">>>>> Custom Exception : ", e);
		BaseErrorCode errorCode = e.getErrorCode();
		return ResponseEntity.status(errorCode.getHttpStatus()).body(
			ApiResponse.onFailure(
				errorCode.getCode(),
				errorCode.getMessage()
			)
		);
	}

	@ExceptionHandler({DataIntegrityViolationException.class})
	public ApiResponse<Object> handleIntegrityConstraint(DataIntegrityViolationException e) {
		OrganizationErrorCode errorStatus = OrganizationErrorCode.ORGANIZATION_EMAIL_DUPLICATE;
		return ApiResponse.onFailure(
			errorStatus.getCode(),
			errorStatus.getMessage(),
			e.getMessage()
		);
	}

	// ResponseEntityExceptionHandler에서 기본적으로 handle하는 예외는 이렇게 구현해야 함
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(
		MethodArgumentNotValidException e,
		HttpHeaders headers,
		HttpStatusCode status,
		WebRequest request
	) {
		OrganizationErrorCode errorStatus = OrganizationErrorCode.INVALID_FORMAT;

		return ResponseEntity
			.status(errorStatus.getHttpStatus().value())
			.body(ApiResponse.onFailure(
				errorStatus.getCode(),
				errorStatus.getMessage(),
				makeErrorMessages(e)
			));
	}

	private static List<String> makeErrorMessages(BindException e) {
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

		return fieldErrors.stream()
			.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
			.toList();
	}
}
