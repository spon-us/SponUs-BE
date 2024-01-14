package com.sponus.sponusbe.domain.organization.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.sponusbe.domain.organization.dto.OrganizationJoinRequest;
import com.sponus.sponusbe.domain.organization.dto.OrganizationJoinResponse;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.organization.repository.OrganizationRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrganizationService {

	private final OrganizationRepository organizationRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public OrganizationJoinResponse join(OrganizationJoinRequest request) {
		final Organization organization = organizationRepository.save(
			request.toEntity(passwordEncoder.encode(request.password())));
		return OrganizationJoinResponse.from(organization);
	}
}