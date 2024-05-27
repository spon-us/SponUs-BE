package com.sponus.coredomain.domain.organization;

import com.sponus.coredomain.domain.organization.enums.CollaborationType;
import com.sponus.coredomain.domain.organization.enums.CompanyType;
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
@Table(name = "company")
@DiscriminatorValue("COMPANY")
public class Company extends Organization {

	@Enumerated(EnumType.STRING)
	@Column(name = "collaboration_type")
	private CollaborationType collaborationType;

	@Column(name = "sponsorship_content")
	private String sponsorshipContent;

	@Enumerated(EnumType.STRING)
	@Column(name = "company_type")
	private CompanyType companyType;

	// 회사 URL

	// 초기 생성 시 사용
	public Company(String name, String email, String password) {
		super(name, email, password, null, null, OrganizationType.COMPANY, ProfileStatus.INACTIVE, Role.GUEST);
	}

	// 프로필 업데이트 시 사용
	public void updateInfo(
		String name,
		String description,
		String imageUrl,
		CollaborationType collaborationType,
		String sponsorshipContent,
		CompanyType companyType,
		ProfileStatus profileStatus
	) {
		super.updateInfo(name, description, imageUrl, profileStatus);
		this.collaborationType = collaborationType;
		this.sponsorshipContent = sponsorshipContent;
		this.companyType = companyType;
	}
}
