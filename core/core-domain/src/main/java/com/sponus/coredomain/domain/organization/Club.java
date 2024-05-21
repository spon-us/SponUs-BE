package com.sponus.coredomain.domain.organization;

import com.sponus.coredomain.domain.organization.enums.ClubType;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.coredomain.domain.organization.enums.ProfileStatus;
import com.sponus.coredomain.domain.organization.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

	@Enumerated(EnumType.STRING)
	@Column(name = "company_type")
	private ClubType clubType;

	// 동아리 URL

	// 초기 생성 시 사용
	public Club(String name, String email, String password) {
		super(name, email, password, null, null, OrganizationType.COMPANY, ProfileStatus.INACTIVE, Role.GUEST);
	}

	// 프로필 업데이트 시 사용
	public void updateInfo(
		String name,
		String description,
		String imageUrl,
		int memberCount,
		ClubType clubType,
		ProfileStatus profileStatus) {
		super.updateInfo(name, description, imageUrl, profileStatus, Role.USER);
		this.memberCount = memberCount;
		this.clubType = clubType;
	}
}
