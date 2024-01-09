package com.sponus.sponusbe.auth.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sponus.sponusbe.group.entity.Group;
import com.sponus.sponusbe.group.repository.GroupRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	private final GroupRepository groupRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Group findGroup = groupRepository.findGroupByEmail(email);

		log.info("[*] Group found : " + findGroup.getEmail());

		return new CustomUserDetails(findGroup);
	}
}
