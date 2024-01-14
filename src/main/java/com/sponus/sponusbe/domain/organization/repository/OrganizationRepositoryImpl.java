package com.sponus.sponusbe.domain.organization.repository;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.sponus.sponusbe.domain.organization.entity.Organization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class OrganizationRepositoryImpl implements OrganizationRepository {

	private final OrganizationJpaRepository organizationJpaRepository;

	@Override
	public Organization save(Organization organization) {
		return organizationJpaRepository.save(organization);
	}

	@Override
	public Organization findOrganizationByEmail(String email) {
		return organizationJpaRepository.findOrganizationByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("이메일에 해당되는 단체를 찾을 수 없습니다."));
	}
}
