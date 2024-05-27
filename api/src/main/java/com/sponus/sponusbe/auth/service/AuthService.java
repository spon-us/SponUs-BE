package com.sponus.sponusbe.auth.service;

import org.springframework.stereotype.Service;

import com.sponus.coredomain.domain.organization.repository.OrganizationRepository;
import com.sponus.coreinfraemail.EmailUtil;
import com.sponus.coreinfrasecurity.jwt.util.JwtUtil;
import com.sponus.sponusbe.auth.dto.EmailVerificationResponse;
import com.sponus.sponusbe.auth.dto.ReissueResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

	private final JwtUtil jwtUtil;
	private final OrganizationRepository organizationRepository;
	private final EmailUtil emailUtil;

	public ReissueResponse reissueToken(String refreshToken) {
		return ReissueResponse.from(jwtUtil.reissueToken(refreshToken));
	}

	public EmailVerificationResponse verifyDuplicateEmail(String email) {
		String emailExists =
			Boolean.TRUE.equals(organizationRepository.checkDuplicateEmail(email)) ? "EXIST" : "NOT_EXIST";
		return EmailVerificationResponse.from(email, emailExists);
	}
}
