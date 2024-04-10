package com.sponus.sponusbe.organization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.sponus.sponusbe.domain.organization.service.OrganizationService;

@SpringBootTest
@AutoConfigureMockMvc
	// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrganizationServiceTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private OrganizationService organizationService;

	@Test
	void beanTest() {
		Assertions.assertNotNull(organizationService);
	}

	// @Order(1)
	// @Test
	// void joinTest() {
	//
	// 	// given
	// 	OrganizationJoinRequest request = new OrganizationJoinRequest(""
	// 		+ "스포너스 테스트", "sponus_test@gmail.com", "password1234", OrganizationType.COMPANY, null);
	//
	// 	// when
	// 	OrganizationJoinResponse response = organizationService.join(request);
	//
	// 	// then
	// 	Assertions.assertEquals(request.name(), response.name());
	// 	System.out.println("[*] 회원가입 테스트: " + response);
	// }
	//
	// @Order(2)
	// @Test
	// void loginTest() throws Exception {
	//
	// 	// given
	// 	String jsonRequest = "{"
	// 		+ "\"email\": \"sponus_test@gmail.com\", "
	// 		+ "\"password\": \"password1234\", "
	// 		+ "\"fcmToken\": \"fcmTokenFcmTokenFcmTokenFcmTokenFcmTokenFcmTokenFcmTokenFcmTokenFcmTokenFcmTokenFcmTokenFcmToken\""
	// 		+ "}";
	//
	// 	// when
	// 	MvcResult response = mockMvc.perform(
	// 			MockMvcRequestBuilders.post("/api/v1/organizations/login")
	// 				.contentType(MediaType.APPLICATION_JSON)
	// 				.content(jsonRequest)) // JSON 형식의 데이터를 본문에 추가
	// 		.andReturn();
	//
	// 	// then
	// 	Assertions.assertEquals(HttpStatus.CREATED.value(), response.getResponse().getStatus());
	// 	System.out.println("[*] 로그인 테스트: " + response.getResponse().getContentAsString());
	// }
}
