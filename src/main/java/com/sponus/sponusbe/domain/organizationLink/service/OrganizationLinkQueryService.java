package com.sponus.sponusbe.domain.organizationLink.service;

import com.sponus.sponusbe.domain.organization.exception.OrganizationException;
import com.sponus.sponusbe.domain.organizationLink.dto.response.OrganizationLinkGetResponse;
import com.sponus.sponusbe.domain.organizationLink.entity.OrganizationLink;
import com.sponus.sponusbe.domain.organizationLink.repository.OrganizationLinkRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import static com.sponus.sponusbe.domain.organizationLink.exception.OrganizationLinkErrorCode.ORGANIZATION_LINK_NOT_FOUND;

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
