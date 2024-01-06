package com.sponus.sponusbe.member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.sponusbe.global.common.ApiResponse;
import com.sponus.sponusbe.member.dto.MemberJoinRequest;
import com.sponus.sponusbe.member.dto.MemberJoinResponse;
import com.sponus.sponusbe.member.entity.Member;
import com.sponus.sponusbe.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/join")
	public ApiResponse<MemberJoinResponse> join(@Valid @RequestBody MemberJoinRequest request) {
		Member newMember = memberService.join(request);
		return ApiResponse.onSuccess(MemberJoinResponse.from(newMember));
	}
}
