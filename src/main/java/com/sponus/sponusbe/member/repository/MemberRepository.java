package com.sponus.sponusbe.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponus.sponusbe.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Member findMemberByEmail(String email);
}
