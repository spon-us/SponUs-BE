package com.sponus.sponusbe.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

	MEMBER("MEMBER"),
	COMPANY("COMPANY"),
	;

	private final String value;
}
