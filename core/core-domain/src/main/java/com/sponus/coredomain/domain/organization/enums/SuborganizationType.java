package com.sponus.coredomain.domain.organization.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SuborganizationType {

	STUDENT_COUNCIL("STUDENT_COUNCIL", "Student Council"),
	CLUB("CLUB", "Club"),
	;

	private final String value;
	private final String name;

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
