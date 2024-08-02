package com.sponus.coredomain.domain.organization.repository.querydsl.conditions;

import lombok.Builder;

@Builder
public record OrganizationSearchCondition(
	String keyword,
	Long organizationId
) {
	public static OrganizationSearchCondition of(String keyword, Long organizationId) {
		return OrganizationSearchCondition.builder()
			.keyword(keyword)
			.organizationId(organizationId)
			.build();
	}
}
