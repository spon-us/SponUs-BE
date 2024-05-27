package com.sponus.sponusbe.auth.dto;

import lombok.Builder;

@Builder
public record SendAuthenticationCodeResponse(
	String email,
	String code
) {

	public static SendAuthenticationCodeResponse from(String email, String code){
		return SendAuthenticationCodeResponse.builder()
			.email(email)
			.code(code)
			.build();
	}
}
