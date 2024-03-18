package com.sponus.sponusbe.organization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.sponusbe.domain.organization.dto.OrganizationJoinRequest;
import com.sponus.sponusbe.domain.organization.dto.OrganizationJoinResponse;
import com.sponus.sponusbe.domain.organization.service.OrganizationService;

@SpringBootTest
@AutoConfigureMockMvc
public class OrganizationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private OrganizationService organizationService;

	@Test
	public void joinTest() {
		// given
		OrganizationJoinRequest request = new OrganizationJoinRequest(""
			+ "스포너스 테스트", "sponus_test@gmail.com", "password1234", OrganizationType.COMPANY, null);
		// when
		OrganizationJoinResponse response = organizationService.join(request);
		// then
		Assertions.assertEquals(request.name(), response.name());
	}
}
