package com.sponus.sponusbe.auth.jwt.filter;

import static com.sponus.sponusbe.auth.jwt.exception.status.TokenErrorCode.*;
import static com.sponus.sponusbe.auth.jwt.util.ResponseUtil.*;
import static org.springframework.http.HttpStatus.*;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.sponus.sponusbe.auth.jwt.exception.CustomExpiredJwtException;
import com.sponus.sponusbe.auth.jwt.exception.CustomMalformedException;
import com.sponus.sponusbe.auth.jwt.exception.CustomNoTokenException;
import com.sponus.sponusbe.auth.jwt.exception.CustomSignatureException;
import com.sponus.sponusbe.global.common.ApiResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

public class JwtExceptionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain)
		throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (CustomExpiredJwtException e) {
			logger.warn(e.getMessage());
			setErrorResponse(response, TOKEN_EXPIRED.getHttpStatus());
		} catch (CustomMalformedException e) {
			logger.warn(e.getMessage());
			setErrorResponse(response, INVALID_FORM_TOKEN.getHttpStatus());
		} catch (CustomNoTokenException e) {
			logger.warn(e.getMessage());
			setErrorResponse(response, NO_TOKEN.getHttpStatus());
		} catch (CustomSignatureException e) {
			logger.warn(e.getMessage());
			setErrorResponse(response, SIGNATURE_ERROR.getHttpStatus());
		} catch (Exception e) {
			logger.warn(e.getMessage());
			logger.warn(">>>>> Internal Server Error : ", e);

			response.getWriter().print(
				ApiResponse.onFailure(
					INTERNAL_SERVER_ERROR.name(),
					INTERNAL_SERVER_ERROR.getReasonPhrase(),
					e.getMessage()
				).toJsonString()
			);
		}
	}
}
