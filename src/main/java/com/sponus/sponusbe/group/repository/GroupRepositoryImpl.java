package com.sponus.sponusbe.group.repository;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.sponus.sponusbe.group.entity.Group;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class GroupRepositoryImpl implements GroupRepository {

	private final GroupJpaRepository groupJpaRepository;

	@Override
	public Group save(Group group) {
		return groupJpaRepository.save(group);
	}

	@Override
	public Group findGroupByEmail(String groupEmail) {
		return groupJpaRepository.findGroupByEmail(groupEmail)
			.orElseThrow(() -> new UsernameNotFoundException("이메일에 해당되는 유저를 찾을 수 없습니다."));
	}
}
