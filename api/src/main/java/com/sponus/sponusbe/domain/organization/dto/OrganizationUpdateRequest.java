package com.sponus.sponusbe.domain.organization.dto;

import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.coredomain.domain.organization.enums.SuborganizationType;

public record OrganizationUpdateRequest(
	String name,
	String email,
	String password,
	String location,
	String description,
	OrganizationType organizationType,
	SuborganizationType suborganizationType,
	String managerName,
	String managerPosition,
	String managerEmail,
	String managerPhone,
	String managerAvailableDay,
	String managerAvailableHour,
	String managerContactPreference
) {
}
