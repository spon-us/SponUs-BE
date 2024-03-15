package com.sponus.coreinfrasecurity.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.repository.OrganizationRepository;
import com.sponus.coreinfrasecurity.jwt.exception.SecurityCustomException;
import com.sponus.coreinfrasecurity.jwt.exception.SecurityErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	private final OrganizationRepository organizationRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Organization findOrganization = organizationRepository.findOrganizationByEmail(email)
			.orElseThrow(() -> new SecurityCustomException(SecurityErrorCode.TOKEN_ORGANIZATION_NOT_FOND));

		log.info("[*] Organization found : " + findOrganization.getEmail());

		return new CustomUserDetails(findOrganization);
	}
}
