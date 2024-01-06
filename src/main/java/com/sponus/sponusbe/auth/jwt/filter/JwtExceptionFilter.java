package com.sponus.sponusbe.auth.jwt.filter;

import static com.sponus.sponusbe.auth.jwt.util.ResponseUtil.*;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.sponus.sponusbe.auth.jwt.exception.CustomExpiredJwtException;
import com.sponus.sponusbe.auth.jwt.exception.CustomMalformedException;
import com.sponus.sponusbe.auth.jwt.exception.CustomNoTokenException;
import com.sponus.sponusbe.auth.jwt.exception.CustomSignatureException;
import com.sponus.sponusbe.auth.jwt.exception.status.TokenErrorStatus;
import com.sponus.sponusbe.global.common.ApiResponse;
import com.sponus.sponusbe.global.common.status.CommonErrorStatus;

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
			setResponse(response, TokenErrorStatus.TOKEN_EXPIRED);
		} catch (CustomMalformedException e) {
			logger.warn(e.getMessage());
			setResponse(response, TokenErrorStatus.INVALID_FORM_TOKEN);
		} catch (CustomNoTokenException e) {
			logger.warn(e.getMessage());
			setResponse(response, TokenErrorStatus.NO_TOKEN);
		} catch (CustomSignatureException e) {
			logger.warn(e.getMessage());
			setResponse(response, TokenErrorStatus.SIGNATURE_ERROR);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();

			response.getWriter().print(
				ApiResponse.onFailure(
					CommonErrorStatus.INTERNAL_SERVER_ERROR.getCode(),
					CommonErrorStatus.INTERNAL_SERVER_ERROR.getMessage(),
					e.getMessage()
				).toJsonString()
			);
		}
	}
}
