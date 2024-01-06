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
import com.sponus.sponusbe.global.common.status.MemberErrorStatus;
import com.sponus.sponusbe.member.controller.MemberController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(basePackageClasses = MemberController.class)
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler({Exception.class})
	public ApiResponse<Object> handleAllException(Exception e) {
		MemberErrorStatus errorStatus = MemberErrorStatus.MEMBER_ERROR;
		return handleExceptionInternal(e, errorStatus);
	}

	@ExceptionHandler({DataIntegrityViolationException.class})
	public ApiResponse<Object> handleIntegrityConstraint(DataIntegrityViolationException e) {
		MemberErrorStatus errorStatus = MemberErrorStatus.MEMBER_EMAIL_DUPLICATE;
		return handleExceptionInternal(errorStatus, e.getMessage());
	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(
		MethodArgumentNotValidException e,
		HttpHeaders headers,
		HttpStatusCode status,
		WebRequest request) {
		MemberErrorStatus errorStatus = MemberErrorStatus.INVALID_FORMAT;
		return handleExceptionInternal(e, errorStatus);
	}

	private ApiResponse<Object> handleExceptionInternal(Exception e, MemberErrorStatus errorStatus) {
		return ApiResponse.onFailure(
			errorStatus.getCode(),
			errorStatus.getMessage(),
			e.getMessage()
		);
	}

	private ResponseEntity<Object> handleExceptionInternal(BindException e, MemberErrorStatus errorStatus) {

		List<String> errorMessages = makeErrorMessages(e);

		return ResponseEntity
			.status(errorStatus.getHttpStatus().value())
			.body(ApiResponse.onFailure(
				errorStatus.getCode(),
				errorStatus.getMessage(),
				errorMessages
			));
	}

	private static List<String> makeErrorMessages(BindException e) {
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

		return fieldErrors.stream()
			.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
			.toList();
	}

	private ApiResponse<Object> handleExceptionInternal(MemberErrorStatus errorStatus, String message) {
		return ApiResponse.onFailure(
			errorStatus.getCode(),
			errorStatus.getMessage(),
			message
		);
	}
}
