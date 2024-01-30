package com.sponus.sponusbe.domain.organization.dto;

import java.util.List;

import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.organization.entity.enums.OrganizationStatus;
import com.sponus.sponusbe.domain.organization.entity.enums.OrganizationType;
import com.sponus.sponusbe.domain.organization.entity.enums.SuborganizationType;
import com.sponus.sponusbe.domain.tag.dto.TagGetResponse;

public record OrganizationDetailGetResponse(
	Long organizationId,
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
	String managerContactPreference,
	OrganizationStatus organizationStatus,
	List<TagGetResponse> tags,
	List<OrganizationLinkGetResponse> links
) {
	public static OrganizationDetailGetResponse from(Organization organization) {
		List<TagGetResponse> tagGetResponses = TagGetResponse.getTagResponses(organization);
		List<OrganizationLinkGetResponse> linkGetResponses = OrganizationLinkGetResponse.getOrganizationLinkResponses(
			organization);
		return new OrganizationDetailGetResponse(
			organization.getId(),
			organization.getName(),
			organization.getEmail(),
			organization.getPassword(),
			organization.getLocation(),
			organization.getDescription(),
			organization.getImageUrl(),
			organization.getOrganizationType(),
			organization.getSuborganizationType(),
			organization.getManagerName(),
			organization.getManagerPosition(),
			organization.getManagerEmail(),
			organization.getManagerPhone(),
			organization.getManagerAvailableDay(),
			organization.getManagerAvailableHour(),
			organization.getManagerContactPreference(),
			organization.getOrganizationStatus(),
			tagGetResponses,
			linkGetResponses
		);
	}
}
