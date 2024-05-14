package com.sponus.sponusbe.domain.organizationLink.service;

import static com.sponus.sponusbe.domain.organizationLink.exception.OrganizationLinkErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.organization.OrganizationLink;
import com.sponus.coredomain.domain.organization.repository.OrganizationLinkRepository;
import com.sponus.sponusbe.domain.organization.exception.OrganizationException;
import com.sponus.sponusbe.domain.organizationLink.dto.response.OrganizationLinkGetResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrganizationLinkQueryService {
	private final OrganizationLinkRepository organizationLinkRepository;

	public OrganizationLinkGetResponse getOrganizationLink(Long organizationLinkId) {
		OrganizationLink organizationLink = organizationLinkRepository.findById(organizationLinkId)
			.orElseThrow(() -> new OrganizationException(ORGANIZATION_LINK_NOT_FOUND));

		return OrganizationLinkGetResponse.from(organizationLink);

	}
}
