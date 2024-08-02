package com.sponus.sponusbe.domain.organization;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.coredomain.domain.organization.enums.ProfileStatus;
import com.sponus.sponusbe.domain.organization.dto.request.PageCondition;
import com.sponus.sponusbe.domain.organization.dto.response.OrganizationSearchResponse;
import com.sponus.sponusbe.domain.organization.dto.response.PageResponse;
import com.sponus.sponusbe.domain.organization.service.OrganizationQueryService;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class OrganizationQueryServiceTest {

	@Autowired
	OrganizationQueryService organizationQueryService;

	@Autowired
	EntityManager em;

	@BeforeEach
	public void init() {
		for (int i = 1; i <= 5; i++) {
			Organization organization = Organization.builder()
				.email("sponus_company" + i + "@gmail.com")
				.name("sponus_company" + i)
				.password("sponus_company1234#")
				.organizationType(OrganizationType.COMPANY)
				.profileStatus(ProfileStatus.ACTIVE)
				.build();

			em.persist(organization);
		}
		em.flush();
		em.clear();
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	void searchOrganizationTestV1() {
		// given
		PageCondition pageCondition = new PageCondition(0, 10);
		Organization authOrganization = Organization.builder()
			.email("sponus_company_a@gmail.com")
			.name("sponus_company")
			.password("sponus_company1234#")
			.organizationType(OrganizationType.COMPANY)
			.profileStatus(ProfileStatus.ACTIVE)
			.build();
		em.persist(authOrganization);

		// when
		PageResponse<OrganizationSearchResponse> searchOrganizations = organizationQueryService.searchOrganizations(
			pageCondition, "sponus", authOrganization);

		// then
		List<String> expectedOrganizationNames = List.of(
			"sponus_company1",
			"sponus_company2",
			"sponus_company3",
			"sponus_company4",
			"sponus_company5");
		List<String> actualOrganizationNames = searchOrganizations.content().stream()
			.map(OrganizationSearchResponse::name)
			.toList();

		Assertions.assertThat(actualOrganizationNames).containsExactlyInAnyOrderElementsOf(expectedOrganizationNames);
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	void searchOrganizationTestV2() {
		// given
		PageCondition pageCondition = new PageCondition(0, 10);
		Organization authOrganization = Organization.builder()
			.email("sponus_company_b@gmail.com")
			.name("sponus_company")
			.password("sponus_company1234#")
			.organizationType(OrganizationType.COMPANY)
			.profileStatus(ProfileStatus.ACTIVE)
			.build();
		em.persist(authOrganization);

		// when
		PageResponse<OrganizationSearchResponse> searchOrganizations = organizationQueryService.searchOrganizationsV2(
			pageCondition, "sponus", authOrganization);

		// then
		List<String> expectedOrganizationNames = List.of(
			"sponus_company1",
			"sponus_company2",
			"sponus_company3",
			"sponus_company4",
			"sponus_company5");
		List<String> actualOrganizationNames = searchOrganizations.content().stream()
			.map(OrganizationSearchResponse::name)
			.toList();

		Assertions.assertThat(actualOrganizationNames).containsExactlyInAnyOrderElementsOf(expectedOrganizationNames);
	}
}
