package com.sponus.sponusbe.domain.organization;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.coredomain.domain.organization.repository.OrganizationRepository;
import com.sponus.sponusbe.domain.organization.dto.request.OrganizationCreateRequest;
import com.sponus.sponusbe.domain.organization.service.OrganizationService;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class SecurityAuthTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	OrganizationRepository organizationRepository;
	@Autowired
	OrganizationService organizationService;

	@Test
	void joinTest() {

		// given
		OrganizationCreateRequest request = new OrganizationCreateRequest("sponus_company@gmail.com",
			"sponus_company1234#",
			"sponus_company", OrganizationType.COMPANY);

		// when
		Long organizationId = organizationService.createOrganization(request);

		// then
		List<Organization> organizationList = organizationRepository.findAll();
		Assertions.assertThat(organizationList.size()).isEqualTo(1);
	}

	@Test
	void loginTest() throws Exception {

		// given
		OrganizationCreateRequest request = new OrganizationCreateRequest("sponus_club@gmail.com",
			"sponus_club1234#",
			"sponus_club", OrganizationType.COMPANY);

		organizationService.createOrganization(request);

		// when
		String jsonRequest = "{"
			+ "\"email\": \"sponus_club@gmail.com\", "
			+ "\"password\": \"sponus_club1234#\", "
			+ "\"fcmToken\": \"fcmTokenFcmTokenFcmTokenFcmTokenFcmTokenFcmTokenFcmTokenFcmTokenFcmTokenFcmTokenFcmTokenFcmToken\""
			+ "}";

		MvcResult response = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/v2/auth/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonRequest)) // JSON 형식의 데이터를 본문에 추가
			.andReturn();

		// then
		Assertions.assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}
}
