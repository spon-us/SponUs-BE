package com.sponus.sponusbe.domain.organization.dto;

import java.util.List;

import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.organization.entity.OrganizationLink;

public record OrganizationLinkGetResponse(
	Long organizationLinkId,
	Long organizationId,
	String name,
	String url
) {
	public static OrganizationLinkGetResponse from(OrganizationLink organizationLink) {
		return new OrganizationLinkGetResponse(
			organizationLink.getId(),
			organizationLink.getOrganization().getId(),
			organizationLink.getName(),
			organizationLink.getUrl()
		);
	}

	public static List<OrganizationLinkGetResponse> getOrganizationLinkResponses(Organization organization) {
		return organization.getOrganizationLinks().stream()
			.map(OrganizationLinkGetResponse::from)
			.toList();
	}
}
