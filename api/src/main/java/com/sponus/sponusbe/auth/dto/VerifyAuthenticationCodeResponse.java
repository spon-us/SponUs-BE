package com.sponus.sponusbe.auth.dto;

import lombok.Builder;

@Builder
public record VerifyAuthenticationCodeResponse(
	String email,
	String flag
) {

	public static VerifyAuthenticationCodeResponse from(String email, String flag) {
		return VerifyAuthenticationCodeResponse.builder()
			.email(email)
			.flag(flag)
			.build();
	}
}
