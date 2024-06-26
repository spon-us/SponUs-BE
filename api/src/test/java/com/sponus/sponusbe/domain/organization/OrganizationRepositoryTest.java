package com.sponus.sponusbe.domain.organization;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.coredomain.domain.organization.enums.ProfileStatus;
import com.sponus.coredomain.domain.organization.repository.OrganizationRepository;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class OrganizationRepositoryTest {

	@Autowired
	OrganizationRepository organizationRepository;

	@Test
	void saveTest() {
		// given
		Organization entity = Organization.builder()
			.email("sponus_company@gmail.com")
			.name("sponus_company")
			.password("sponus_company1234#")
			.organizationType(OrganizationType.COMPANY)
			.profileStatus(ProfileStatus.ACTIVE)
			.build();

		// when
		Organization savedEntity = organizationRepository.save(entity);

		// then
		Assertions.assertThat(entity.getEmail()).isEqualTo(savedEntity.getEmail());
	}
}
