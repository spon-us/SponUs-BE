package com.sponus.sponusbe.domain.organization.service;

import static com.sponus.sponusbe.domain.organization.exception.OrganizationErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.sponusbe.domain.organization.dto.OrganizationDetailGetResponse;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.organization.exception.OrganizationException;
import com.sponus.sponusbe.domain.organization.repository.OrganizationRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrganizationQueryService {
	private final OrganizationRepository organizationRepository;

	public OrganizationDetailGetResponse getOrganization(Long organizationId) {
		Organization organization = organizationRepository.findById(organizationId)
			.orElseThrow(() -> new OrganizationException(ORGANIZATION_NOT_FOUND));

		return OrganizationDetailGetResponse.from(organization);
	}
}
