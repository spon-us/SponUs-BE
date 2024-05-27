package com.sponus.sponusbe.auth.dto;

import lombok.Builder;

@Builder
public record EmailAuthenticationResponse(
	String email,
	String code
) {

	public static EmailAuthenticationResponse from(String email, String code){
		return EmailAuthenticationResponse.builder()
			.email(email)
			.code(code)
			.build();
	}
}
