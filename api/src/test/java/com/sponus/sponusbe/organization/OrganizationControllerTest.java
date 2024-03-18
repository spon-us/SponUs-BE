package com.sponus.sponusbe.organization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.sponusbe.domain.organization.dto.OrganizationJoinRequest;
import com.sponus.sponusbe.domain.organization.dto.OrganizationJoinResponse;
import com.sponus.sponusbe.domain.organization.service.OrganizationService;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrganizationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private OrganizationService organizationService;

	@Order(1)
	@Test
	void joinTest() {

		// given
		OrganizationJoinRequest request = new OrganizationJoinRequest(""
			+ "스포너스 테스트", "sponus_test@gmail.com", "password1234", OrganizationType.COMPANY, null);
		// when
		OrganizationJoinResponse response = organizationService.join(request);
		// then
		Assertions.assertEquals(request.name(), response.name());
		System.out.println("[*] 회원가입 테스트: " + response);
	}

	@Order(2)
	@Test
	void loginTest() throws Exception {

		// given
		String jsonRequest = "{\"email\": \"sponus_test@gmail.com\", \"password\": \"password1234\", \"fcmToken\": \"fUhUFOE6uEM8rUJ48kiQM2:APA91bGIqRztEpmgv34yFag6sjBRGXgkza4Gh-CqMjLHdp3jSFT25EFeUgBMNt6UWrcwJzaZ1daO0a2H1iSUNReS0A524XKVb_eulcgV4SXRL9lxe1Zc6fDLQQJd4egnjdzDDzJKQ27f\"}";

		// when
		MvcResult response = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/v1/organizations/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonRequest)) // JSON 형식의 데이터를 본문에 추가
			.andReturn();

		// then
		Assertions.assertEquals(HttpStatus.CREATED.value(), response.getResponse().getStatus());
		System.out.println("[*] 로그인 테스트: " + response.getResponse().getContentAsString());
	}

}
