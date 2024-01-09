package com.sponus.sponusbe.group.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SubGroupType {

	STUDENT_COUNCIL("STUDENT_COUNCIL"),
	CLUB("CLUB"),
	;

	private final String value;

	@JsonCreator
	public static SubGroupType from(String ipt) {
		for (SubGroupType subGroupValue : SubGroupType.values()) {
			if (subGroupValue.getValue().equals(ipt)) {
				return subGroupValue;
			}
		}
		return null;
	}
}
