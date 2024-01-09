package com.sponus.sponusbe.group.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GroupType {

	STUDENT("STUDENT"),
	COMPANY("COMPANY"),
	;

	private final String value;

	@JsonCreator
	public static GroupType from(String ipt) {
		for (GroupType groupValue : GroupType.values()) {
			if (groupValue.getValue().equals(ipt)) {
				return groupValue;
			}
		}
		return null;
	}
}
