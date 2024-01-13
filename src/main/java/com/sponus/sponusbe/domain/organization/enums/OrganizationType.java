package com.sponus.sponusbe.domain.organization.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrganizationType {

	STUDENT("STUDENT"),
	COMPANY("COMPANY"),
	;

	private final String value;

	@JsonCreator
	public static OrganizationType from(String ipt) {
		for (OrganizationType groupValue : OrganizationType.values()) {
			if (groupValue.getValue().equals(ipt)) {
				return groupValue;
			}
		}
		return null;
	}
}
