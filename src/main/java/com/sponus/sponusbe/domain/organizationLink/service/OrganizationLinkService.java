package com.sponus.sponusbe.domain.organizationLink.service;

import static com.sponus.sponusbe.domain.organization.exception.OrganizationErrorCode.*;
import static com.sponus.sponusbe.domain.organizationLink.exception.OrganizationLinkErrorCode.ORGANIZATION_LINK_NOT_FOUND;

import com.sponus.sponusbe.domain.organizationLink.dto.request.OrganizationLinkUpdateRequest;
import com.sponus.sponusbe.domain.organizationLink.exception.OrganizationLinkException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.organization.exception.OrganizationException;
import com.sponus.sponusbe.domain.organization.repository.OrganizationRepository;
import com.sponus.sponusbe.domain.organizationLink.dto.request.OrganizationLinkCreateRequest;
import com.sponus.sponusbe.domain.organizationLink.dto.response.OrganizationLinkCreateResponse;
import com.sponus.sponusbe.domain.organizationLink.entity.OrganizationLink;
import com.sponus.sponusbe.domain.organizationLink.repository.OrganizationLinkRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrganizationLinkService {
	private final OrganizationRepository organizationRepository;
	private final OrganizationLinkRepository organizationLinkRepository;

	@Transactional
	public OrganizationLinkCreateResponse createOrganizationLink(Long organizationId,
		OrganizationLinkCreateRequest request) {
		Organization organization = organizationRepository.findById(organizationId)
			.orElseThrow(() -> new OrganizationException(ORGANIZATION_NOT_FOUND));

		OrganizationLink organizationLink = OrganizationLink.builder()
			.name(request.name())
			.url(request.url())
			.organization(organization)
			.build();

		organizationLink = organizationLinkRepository.save(organizationLink);
		organization.getOrganizationLinks().add(organizationLink);

		return new OrganizationLinkCreateResponse(organizationLink.getId());
	}

	@Transactional
    public void updateOrganizationLink(Long organizationLinkId, OrganizationLinkUpdateRequest request) {
		OrganizationLink organizationLink = organizationLinkRepository.findById(organizationLinkId)
				.orElseThrow(() -> new OrganizationLinkException(ORGANIZATION_LINK_NOT_FOUND));

		organizationLink.update(request);
    }

	public void deleteOrganizationLink(Long organizationLinkId) {
		organizationLinkRepository.deleteById(organizationLinkId);
	}
}
