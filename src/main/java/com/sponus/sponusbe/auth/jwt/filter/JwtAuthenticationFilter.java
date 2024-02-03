package com.sponus.sponusbe.auth.jwt.filter;

import static com.sponus.sponusbe.auth.jwt.util.HttpResponseUtil.*;
import static org.springframework.http.HttpStatus.*;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sponus.sponusbe.auth.jwt.dto.JwtPair;
import com.sponus.sponusbe.auth.jwt.exception.SecurityCustomException;
import com.sponus.sponusbe.auth.jwt.exception.SecurityErrorCode;
import com.sponus.sponusbe.auth.jwt.util.JwtUtil;
import com.sponus.sponusbe.auth.jwt.util.RedisUtil;
import com.sponus.sponusbe.auth.user.CustomUserDetails;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final RedisUtil redisUtil;

	@Override
	protected void doFilterInternal(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain
	) throws ServletException, IOException {
		logger.info("[*] Jwt Filter");

		try {
			String accessToken = jwtUtil.resolveAccessToken(request);

			// accessToken 없이 접근할 경우
			if (accessToken == null) {
				filterChain.doFilter(request, response);
				return;
			}

			// logout 처리된 accessToken
			if (redisUtil.get(accessToken) != null && redisUtil.get(accessToken).equals("logout")) {
				logger.info("[*] Logout accessToken");
				filterChain.doFilter(request, response);
				return;
			}

			logger.info("[*] Authorization with Token");
			authenticateAccessToken(accessToken);
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			logger.warn("[*] case : accessToken Expired");
			// accessToken 만료 시 Body에 있는 refreshToken 확인
			String refreshToken = request.getHeader("refreshToken");

			logger.info("[*] refreshToken : " + refreshToken);
			try {
				if (jwtUtil.validateRefreshToken(refreshToken)) {
					logger.info("[*] case : accessToken Expired && refreshToken in redis");
					// refreshToken 유효 시 재발급
					JwtPair reissueTokens = jwtUtil.reissueToken(refreshToken);
					setSuccessResponse(response, CREATED, reissueTokens);
				}
			} catch (ExpiredJwtException eje) {
				logger.info("[*] case : accessToken, refreshToken expired");
				throw new SecurityCustomException(SecurityErrorCode.TOKEN_EXPIRED, eje);
			} catch (IllegalArgumentException iae) {
				logger.info("[*] case : Invalid refreshToken");
				throw new SecurityCustomException(SecurityErrorCode.INVALID_TOKEN, iae);
			}
		}
	}

	private void authenticateAccessToken(String accessToken) {
		CustomUserDetails userDetails = new CustomUserDetails(
			jwtUtil.getId(accessToken),
			jwtUtil.getEmail(accessToken),
			null,
			jwtUtil.getAuthority(accessToken)
		);

		logger.info("[*] Authority Registration");

		// 스프링 시큐리티 인증 토큰 생성
		Authentication authToken = new UsernamePasswordAuthenticationToken(
			userDetails,
			null,
			userDetails.getAuthorities());

		// 컨텍스트 홀더에 저장
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}
}