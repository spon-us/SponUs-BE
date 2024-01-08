package com.sponus.sponusbe.auth.user;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sponus.sponusbe.member.entity.Member;
import com.sponus.sponusbe.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Member findMember = memberRepository.findMemberByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("이메일에 해당되는 유저를 찾을 수 없습니다."));

		log.info("[*] User found : " + findMember.getEmail());

		return new CustomUserDetails(findMember);
	}
}
