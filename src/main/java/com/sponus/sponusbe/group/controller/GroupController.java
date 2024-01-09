package com.sponus.sponusbe.group.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.sponusbe.global.common.ApiResponse;
import com.sponus.sponusbe.group.dto.GroupJoinRequest;
import com.sponus.sponusbe.group.dto.GroupJoinResponse;
import com.sponus.sponusbe.group.entity.Group;
import com.sponus.sponusbe.group.service.GroupService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups")
public class GroupController {

	private final GroupService groupService;

	@PostMapping("/join")
	public ApiResponse<GroupJoinResponse> join(@Valid @RequestBody GroupJoinRequest request) {
		Group newGroup = groupService.join(request);
		return ApiResponse.onSuccess(GroupJoinResponse.from(newGroup));
	}
}
