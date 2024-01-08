package com.sponus.sponusbe.member.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.sponusbe.member.dto.MemberJoinRequest;
import com.sponus.sponusbe.member.entity.Member;
import com.sponus.sponusbe.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public Member join(MemberJoinRequest request) {
		return memberRepository.save(
			MemberJoinRequest.toMemberEntity(request, passwordEncoder.encode(request.password()))
		);
	}

}
