package com.sponus.sponusbe.organization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.enums.OrganizationStatus;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.coredomain.domain.organization.repository.OrganizationRepository;

@Transactional
@SpringBootTest
@EnableJpaRepositories(basePackages = "com.sponus.domain.organization.repository")
@EntityScan(basePackages = "com.sponus.domain.organization")
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
	}
}
