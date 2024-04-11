package com.sponus.sponusbe.domain.organization.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.enums.OrganizationStatus;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.coredomain.domain.organization.repository.OrganizationRepository;

@SpringBootTest
class OrganizationRepositoryTest {

	@Autowired
	OrganizationRepository organizationRepository;

	@Test
	void saveTest() {
		// given
		Organization entity = Organization.builder()
			.email("test@naver.com")
			.name("test")
			.password("test1234#")
			.organizationType(OrganizationType.COMPANY)
			.organizationStatus(OrganizationStatus.ACTIVE)
			.build();

		// when
		Organization savedEntity = organizationRepository.save(entity);

		// then
		Assertions.assertEquals(entity.getEmail(), savedEntity.getEmail());
		System.out.println("[*] 테스트입니다. " + savedEntity.getEmail());
	}
}

