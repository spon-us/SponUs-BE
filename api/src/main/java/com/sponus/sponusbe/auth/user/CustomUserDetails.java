package com.sponus.sponusbe.auth.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sponus.coredomain.domain.organization.Organization;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {

	private final Long id;
	private final String email;
	private final String password;
	private final String authority;

	public CustomUserDetails(Long id, String email, String password, String authority) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.authority = authority;
	}

	public CustomUserDetails(Organization organization) {
		this.id = organization.getId();
		this.email = organization.getEmail();
		this.password = organization.getPassword();
		this.authority = organization.getOrganizationType().toString();
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
