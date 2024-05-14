package com.sponus.sponusbe.domain.organizationLink.dto.response;

import java.util.List;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.OrganizationLink;

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
		return null;
		// return organization.getOrganizationLinks().stream()
		// 	.map(OrganizationLinkGetResponse::from)
		// 	.toList();
	}
}
