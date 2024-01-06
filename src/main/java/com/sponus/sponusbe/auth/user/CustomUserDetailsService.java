package com.sponus.sponusbe.auth.user;

import java.util.logging.Logger;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sponus.sponusbe.member.entity.Member;
import com.sponus.sponusbe.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final Logger logger = Logger.getLogger(getClass().getName());
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Member findMember = memberRepository.findMemberByEmail(email);

		if (findMember == null) {
			return null;
		}

		logger.info("[*] User found : " + findMember.getEmail());

		return new CustomUserDetails(findMember);
	}
}
