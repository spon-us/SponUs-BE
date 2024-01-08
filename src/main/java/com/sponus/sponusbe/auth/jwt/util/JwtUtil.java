package com.sponus.sponusbe.auth.jwt.util;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.sponus.sponusbe.auth.jwt.dto.JwtPair;
import com.sponus.sponusbe.auth.jwt.exception.CustomExpiredJwtException;
import com.sponus.sponusbe.auth.user.CustomUserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {

	private final SecretKey secretKey;
	private final Long accessExpMs;
	private final Long refreshExpMs;
	private final RedisUtil redisUtil;

	public JwtUtil(
		@Value("${spring.jwt.secret}") String secret,
		@Value("${spring.jwt.token.access-expiration-time}") Long access,
		@Value("${spring.jwt.token.refresh-expiration-time}") Long refresh,
		RedisUtil redis) {

		secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm());
		accessExpMs = access;
		refreshExpMs = refresh;
		redisUtil = redis;
	}

	public UserDetails getAuthInfo(String token) {

		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
			.get("auth", CustomUserDetails.class);
	}

	public String getUsername(String token) throws SignatureException {

		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
			.get("auth", CustomUserDetails.class).getUsername();
	}

	public String getRole(String token) throws SignatureException {

		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
			.get("auth", CustomUserDetails.class).getAuthorities().toString();
	}

	public Boolean isExpired(String token) throws SignatureException {

		// 여기서 토큰 형식 이상한 것도 걸러짐
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration()
			.before(new Date());
	}

	public Long getExpTime(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration()
			.getTime();
	}

	public String createJwtAccessToken(CustomUserDetails customUserDetails) {

		return Jwts.builder()
			.header()
			.add("alg", "HS256")
			.add("typ", "JWT")
			.and()
			.claim("auth", customUserDetails)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + accessExpMs))
			.signWith(secretKey)
			.compact();
	}

	public String createJwtRefreshToken(CustomUserDetails customUserDetails) {

		Instant issuedAt = Instant.now();
		Instant expiration = issuedAt.plusMillis(refreshExpMs);

		String refreshToken = Jwts.builder()
			.header()
			.add("alg", "HS256")
			.add("typ", "JWT")
			.and()
			.claim("auth", customUserDetails)
			.issuedAt(Date.from(issuedAt))
			.expiration(Date.from(expiration))
			.signWith(secretKey)
			.compact();

		redisUtil.save(
			customUserDetails.getUsername(),
			refreshToken,
			refreshExpMs,
			TimeUnit.MILLISECONDS
		);

		return refreshToken;
	}

	public JwtPair reissueToken(String refreshToken) throws SignatureException {

		UserDetails authInfo = getAuthInfo(refreshToken);

		return new JwtPair(
			createJwtAccessToken((CustomUserDetails)authInfo),
			createJwtRefreshToken((CustomUserDetails)authInfo)
		);
	}

	public String resolveAccessToken(HttpServletRequest request) {

		String authorization = request.getHeader("Authorization");

		if (authorization == null || !authorization.startsWith("Bearer ")) {

			log.warn("[*] No token in req");

			return null;
		}

		log.info("[*] Token exists");

		return authorization.split(" ")[1];
	}

	public boolean validateRefreshToken(String refreshToken) {

		// refreshToken 유효성 검증
		String useremail = getUsername(refreshToken);

		//redis에 refreshToken 있는지 검증
		if (!redisUtil.hasKey(useremail)) {

			throw new CustomExpiredJwtException();
		}
		return true;
	}
}
