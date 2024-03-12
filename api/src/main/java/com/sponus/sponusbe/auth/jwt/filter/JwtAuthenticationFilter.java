package com.sponus.sponusbe.auth.jwt.filter;

import static com.sponus.sponusbe.auth.jwt.exception.SecurityErrorCode.*;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sponus.coreinfraredis.util.RedisUtil;
import com.sponus.sponusbe.auth.jwt.exception.SecurityCustomException;
import com.sponus.sponusbe.auth.jwt.util.JwtUtil;
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
			throw new SecurityCustomException(TOKEN_EXPIRED);
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
