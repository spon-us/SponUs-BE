package com.sponus.sponusbe.auth.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sponus.sponusbe.group.entity.Group;

public class CustomUserDetails implements UserDetails {

	private final String email;
	private final String password;
	private final String authority;

	public CustomUserDetails(Group group) {
		this.email = group.getEmail();
		this.password = group.getPassword();
		this.authority = group.getGroupType().toString();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();

		collection.add((GrantedAuthority)() -> authority);

		return collection;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
