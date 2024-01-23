package com.sponus.sponusbe.auth.jwt.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sponus.sponusbe.auth.jwt.exception.SecurityCustomException;
import com.sponus.sponusbe.auth.jwt.exception.SecurityErrorCode;
import com.sponus.sponusbe.auth.jwt.util.HttpResponseUtil;
import com.sponus.sponusbe.global.common.BaseErrorCode;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain) throws IOException {
		// TODO : entrypoint로 처리하도록 변경
		try {
			filterChain.doFilter(request, response);
		} catch (SecurityCustomException e) {
			log.warn(">>>>> SecurityCustomException : ", e);
			BaseErrorCode errorCode = e.getErrorCode();
			HttpResponseUtil.setErrorResponse(
				response,
				errorCode.getHttpStatus(),
				errorCode.getErrorResponse()
			);
		} catch (Exception e) {
			log.error(">>>>> Exception : ", e);
			HttpResponseUtil.setErrorResponse(
				response,
				HttpStatus.INTERNAL_SERVER_ERROR,
				SecurityErrorCode.INTERNAL_SECURITY_ERROR.getErrorResponse()
			);
		}
	}
}
