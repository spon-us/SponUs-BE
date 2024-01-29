package com.sponus.sponusbe.domain.organization.dto;

import com.sponus.sponusbe.domain.organization.entity.enums.OrganizationType;
import com.sponus.sponusbe.domain.organization.entity.enums.SuborganizationType;

public record OrganizationUpdateRequest(
	String name,
	String email,
	String password,
	String location,
	String description,
	String imageUrl,
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
