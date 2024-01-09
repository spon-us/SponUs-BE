package com.sponus.sponusbe.group.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.sponusbe.group.dto.GroupJoinRequest;
import com.sponus.sponusbe.group.entity.Group;
import com.sponus.sponusbe.group.repository.GroupRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GroupService {

	private final GroupRepository groupRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public Group join(GroupJoinRequest request) {
		return groupRepository.save(
			GroupJoinRequest.toGroupEntity(request, passwordEncoder.encode(request.password()))
		);
	}
}
