package com.sponus.sponusbe.auth.jwt.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coreinfraredis.util.RedisUtil;
import com.sponus.sponusbe.auth.jwt.dto.JwtPair;
import com.sponus.sponusbe.auth.jwt.util.HttpResponseUtil;
import com.sponus.sponusbe.auth.jwt.util.JwtUtil;
import com.sponus.sponusbe.auth.user.CustomUserDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final RedisUtil redisUtil;

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
			throw new AuthenticationServiceException("Error occurred while parsing request body");
		}

		logger.info("[*] Request Body : " + requestBody);

		String email = (String)requestBody.get("email");
		String password = (String)requestBody.get("password");
		String fcmToken = (String)requestBody.get("fcmToken");

		redisUtil.saveAsValue(
			email + "_fcm_token",
			fcmToken,
			999999999L,
			TimeUnit.MILLISECONDS
		);

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

		logger.info("[*] Login with " + customUserDetails.getUsername());

		JwtPair jwtPair = new JwtPair(
			jwtUtil.createJwtAccessToken(customUserDetails),
			jwtUtil.createJwtRefreshToken(customUserDetails)
		);

		HttpResponseUtil.setSuccessResponse(response, HttpStatus.CREATED, jwtPair);
	}

	@Override
	protected void unsuccessfulAuthentication(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull AuthenticationException failed) throws IOException {
		logger.info("[*] Login Fail");
		// TODO : 예외처리 정리
		String errorMessage;
		if (failed instanceof BadCredentialsException) {
			errorMessage = "Bad credentials";
		} else if (failed instanceof LockedException) {
			errorMessage = "Account is locked";
		} else if (failed instanceof DisabledException) {
			errorMessage = "Account is disabled";
		} else if (failed instanceof UsernameNotFoundException) {
			errorMessage = "Account not found";
		} else if (failed instanceof AuthenticationServiceException) {
			errorMessage = "Error occurred while parsing request body";
		} else {
			errorMessage = "Authentication failed";
		}
		HttpResponseUtil.setErrorResponse(
			response, HttpStatus.UNAUTHORIZED,
			ApiResponse.onFailure(
				HttpStatus.BAD_REQUEST.name(),
				errorMessage
			)
		);
	}

	private Map<String, Object> getBody(HttpServletRequest request) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		String line;

		try (BufferedReader bufferedReader = request.getReader()) {
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		}

		String requestBody = stringBuilder.toString();
		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.readValue(requestBody, Map.class);
	}
}
