package com.sponus.sponusbe.auth.jwt.filter;

import static com.sponus.sponusbe.auth.jwt.util.JsonUtil.*;
import static com.sponus.sponusbe.auth.jwt.util.ResponseUtil.*;
import static com.sponus.sponusbe.global.common.status.CommonErrorStatus.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sponus.sponusbe.auth.jwt.dto.JwtPair;
import com.sponus.sponusbe.auth.jwt.util.JwtUtil;
import com.sponus.sponusbe.auth.user.CustomUserDetails;
import com.sponus.sponusbe.global.common.ApiResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	@Override
	public Authentication attemptAuthentication(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response
	) throws AuthenticationException {

		logger.info("[*] Login Filter");

		Map<String, Object> requestBody;
		try {
			requestBody = getBody(request);
		} catch (IOException e) {
			try {
				setResponse(response, BAD_REQUEST);
				return null;
			} catch (IOException ex) {
				return null;
			}
		}

		logger.info("[*] Request Body : " + requestBody);

		String email = (String)requestBody.get("email");
		String password = (String)requestBody.get("password");

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain chain,
		@NonNull Authentication authentication) throws IOException {

		logger.info("[*] Login Success");

		CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();

		String userEmail = customUserDetails.getUsername();

		logger.info("[*] Login with " + userEmail);

		String role = getRole(authentication);

		JwtPair jwtPair = new JwtPair(
			jwtUtil.createJwtAccessToken(userEmail, role),
			jwtUtil.createJwtRefreshToken(userEmail, role)
		);

		setSuccessResponse(response, jwtPair);
	}

	private static String getRole(@NonNull Authentication authentication) {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority auth = iterator.next();

		return auth.getAuthority();
	}

	@Override
	protected void unsuccessfulAuthentication(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull AuthenticationException failed) throws IOException {

		logger.info("[*] Login Fail");

		String errorMessage;
		if (failed instanceof BadCredentialsException) {
			errorMessage = "Bad credentials";
		} else if (failed instanceof LockedException) {
			errorMessage = "Account is locked";
		} else if (failed instanceof DisabledException) {
			errorMessage = "Account is disabled";
		} else if (failed instanceof InternalAuthenticationServiceException) {
			errorMessage = "Account not found";
		} else {
			errorMessage = "Authentication failed";
		}

		setFailureResponse(response, errorMessage);
	}

	private static void setFailureResponse(
		@NonNull HttpServletResponse response,
		@NonNull String errorMessage) throws
		IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(401);

		response.getWriter().print(
			ApiResponse.onFailure(
				String.valueOf(HttpStatus.UNAUTHORIZED.value()),
				HttpStatus.UNAUTHORIZED.name(),
				errorMessage
			).toJsonString()
		);

		closeWriter(response);
	}

	private static void setSuccessResponse(
		@NonNull HttpServletResponse response,
		@NonNull JwtPair jwtPair) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(200);

		response.getWriter().print(
			ApiResponse.onSuccess(jwtPair).toJsonString()
		);

		closeWriter(response);
	}

	private static void closeWriter(HttpServletResponse response) throws IOException {
		response.getWriter().flush();
		response.getWriter().close();
	}
}
