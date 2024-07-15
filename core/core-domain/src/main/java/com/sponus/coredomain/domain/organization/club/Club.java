package com.sponus.coredomain.domain.organization.club;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.coredomain.domain.organization.enums.ProfileStatus;
import com.sponus.coredomain.domain.organization.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "club")
@DiscriminatorValue("CLUB")
public class Club extends Organization {
	@Column(name = "member_count")
	private int memberCount;

	@OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
	private Set<ClubType> clubTypes = new HashSet<>();

	// 초기 생성 시 사용
	public Club(String name, String email, String password) {
		super(name, email, password, null, null, OrganizationType.CLUB, ProfileStatus.INACTIVE, Role.GUEST);
		this.memberCount = 0;
	}

	// 프로필 업데이트 시 사용
	public void updateInfo(
		String name,
		String description,
		String imageUrl,
		int memberCount,
		ProfileStatus profileStatus) {
		super.updateInfo(name, description, imageUrl, profileStatus);
		this.memberCount = memberCount;
	}

	@Override
	public List<String> getSubTypeNames() {
		return clubTypes.stream().map(type -> type.getType().name()).toList();
	}
}
