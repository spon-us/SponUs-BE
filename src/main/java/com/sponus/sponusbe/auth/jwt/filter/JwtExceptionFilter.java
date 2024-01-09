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
			setErrorResponse(response, TOKEN_EXPIRED.getErrorStatus());
		} catch (CustomMalformedException e) {
			logger.warn(e.getMessage());
			setErrorResponse(response, INVALID_FORM_TOKEN.getErrorStatus());
		} catch (CustomNoTokenException e) {
			logger.warn(e.getMessage());
			setErrorResponse(response, NO_TOKEN.getErrorStatus());
		} catch (CustomSignatureException e) {
			logger.warn(e.getMessage());
			setErrorResponse(response, SIGNATURE_ERROR.getErrorStatus());
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();

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
