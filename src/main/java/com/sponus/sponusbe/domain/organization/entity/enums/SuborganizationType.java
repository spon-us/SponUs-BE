package com.sponus.sponusbe.domain.organization.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SuborganizationType {

	STUDENT_COUNCIL("STUDENT_COUNCIL"),
	CLUB("CLUB"),
	;

	private final String value;

	@JsonCreator
	public static SuborganizationType from(String ipt) {
		for (SuborganizationType subGroupValue : SuborganizationType.values()) {
			if (subGroupValue.getValue().equals(ipt)) {
				return subGroupValue;
			}
		}
		return null;
	}
}
