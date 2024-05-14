package com.sponus.coreinfrasecurity.jwt.dto;

public record JwtPair(
	String accessToken,
	String refreshToken
) {
}
