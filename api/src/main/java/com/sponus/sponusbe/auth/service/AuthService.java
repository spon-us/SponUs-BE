package com.sponus.sponusbe.auth.service;

import org.springframework.stereotype.Service;

import com.sponus.coredomain.domain.organization.repository.OrganizationRepository;
import com.sponus.coreinfraemail.EmailUtil;
import com.sponus.coreinfrasecurity.jwt.util.JwtUtil;
import com.sponus.sponusbe.auth.dto.EmailVerificationResponse;
import com.sponus.sponusbe.auth.dto.ReissueResponse;
import com.sponus.sponusbe.auth.dto.SendAuthenticationCodeResponse;
import com.sponus.sponusbe.auth.dto.VerifyAuthenticationCodeResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

	private final JwtUtil jwtUtil;
	private final OrganizationRepository organizationRepository;
	private final EmailUtil emailUtil;

	public ReissueResponse reissueToken(String refreshToken) {
		log.info("[*] Generate new token pair with " + refreshToken);
		return ReissueResponse.from(jwtUtil.reissueToken(refreshToken));
	}

	public EmailVerificationResponse verifyDuplicateEmail(String email) {
		log.info("[*] Check duplicate email... " + email);
		String emailExists =
			Boolean.TRUE.equals(organizationRepository.checkDuplicateEmail(email)) ? "EXIST" : "NOT_EXIST";
		return EmailVerificationResponse.from(email, emailExists);
	}

	public SendAuthenticationCodeResponse sendAuthenticationCode(String email) throws Exception {
		String code = emailUtil.sendEmail(email);
		log.info("[*] email(" + email + ") send code " + code);
		return SendAuthenticationCodeResponse.from(email, code);
	}

	public VerifyAuthenticationCodeResponse verifyAuthenticationCode(String email, String code) {
		log.info("[*] email(" + email + ") verify with code " + code);
		return VerifyAuthenticationCodeResponse.from(email, emailUtil.verifyCode(email, code));
	}
}
