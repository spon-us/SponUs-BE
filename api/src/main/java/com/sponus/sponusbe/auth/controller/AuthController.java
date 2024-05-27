package com.sponus.sponusbe.auth.controller;

import static com.sponus.sponusbe.global.enums.ApiPath.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.sponusbe.auth.dto.SendAuthenticationCodeResponse;
import com.sponus.sponusbe.auth.dto.EmailVerificationResponse;
import com.sponus.sponusbe.auth.dto.ReissueResponse;
import com.sponus.sponusbe.auth.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(AUTH_URI)
@RestController
public class AuthController {

	private final AuthService authService;

	@GetMapping("/reissue")
	public ApiResponse<ReissueResponse> reissueToken(@RequestHeader("RefreshToken") String refreshToken) {
		return ApiResponse.onSuccess(authService.reissueToken(refreshToken));
	}

	@GetMapping("/verify-email")
	public ApiResponse<EmailVerificationResponse> verifyDuplicateEmail(@RequestHeader("email") String email) {
		return ApiResponse.onSuccess(authService.verifyDuplicateEmail(email));
	}

	@GetMapping("/send-code")
	public ApiResponse<SendAuthenticationCodeResponse> sendAuthenticationCode(@RequestHeader("email") String email) throws
		Exception {
		return ApiResponse.onSuccess(authService.sendAuthenticationCode(email));
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
