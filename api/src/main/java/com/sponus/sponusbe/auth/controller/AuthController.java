package com.sponus.sponusbe.auth.controller;

import static com.sponus.sponusbe.global.enums.ApiPath.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coredomain.domain.organization.repository.OrganizationRepository;
import com.sponus.coreinfraemail.EmailUtil;
import com.sponus.coreinfrasecurity.jwt.dto.JwtPair;
import com.sponus.coreinfrasecurity.jwt.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(AUTH_URI)
@RestController
public class AuthController {

	private final JwtUtil jwtUtil;
	private final OrganizationRepository organizationRepository;
	private final EmailUtil emailUtil;

	@GetMapping("/reissue")
	public ApiResponse<JwtPair> reissueToken(@RequestHeader("RefreshToken") String refreshToken) {
		return ApiResponse.onSuccess(jwtUtil.reissueToken(refreshToken));
	}

	@GetMapping("/verify-email")
	public ApiResponse<Map<String, String>> verifyEmail(@RequestHeader("email") String email) {
		String emailExists =
			Boolean.TRUE.equals(organizationRepository.checkDuplicateEmail(email)) ? "EXIST" : "NOT_EXIST";

		Map<String, String> response = new HashMap<>();
		response.put("email", email);
		response.put("exist", emailExists);

		return ApiResponse.onSuccess(response);
	}

	@GetMapping("/send-code")
	public ApiResponse<Map<String, String>> sendAuthenticationEmail(@RequestHeader("email") String email) throws
		Exception {
		Map<String, String> response = new HashMap<>();
		response.put("email", email);
		response.put("code", emailUtil.sendEmail(email));

		return ApiResponse.onSuccess(response);
	}

	@GetMapping("/verify-code")
	public ApiResponse<Map<String, String>> verifyAuthenticationCode(
		@RequestHeader("email") String email,
		@RequestHeader("code") String code) {
		Map<String, String> response = new HashMap<>();

		response.put("email", email);
		response.put("flag", emailUtil.verifyCode(email, code));

		return ApiResponse.onSuccess(response);
	}

}
