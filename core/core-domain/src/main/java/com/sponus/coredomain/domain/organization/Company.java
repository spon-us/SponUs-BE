package com.sponus.coredomain.domain.organization;

import com.sponus.coredomain.domain.organization.enums.CollaborationType;
import com.sponus.coredomain.domain.organization.enums.CompanyType;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.coredomain.domain.organization.enums.ProfileStatus;
import com.sponus.coredomain.domain.organization.enums.Role;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "company")
@Access(AccessType.FIELD)
public class Company extends Organization {

	@Enumerated(EnumType.STRING)
	@Column(name = "company_type", nullable = false)
	private CompanyType companyType;

	@Enumerated(EnumType.STRING)
	@Column(name = "collaboration_type", nullable = false)
	private CollaborationType collaborationType;

	@Enumerated(EnumType.STRING)
	@Column(name = "sponsorship_content", nullable = false)
	private String sponsorshipContent;

	@Builder
	public Company(
		String name,
		String email,
		String password,
		String description,
		String imageUrl,
		ProfileStatus profileStatus,
		Role role,
		CompanyType companyType,
		CollaborationType collaborationType,
		String sponsorshipContent) {
		super(name, email, password, description, imageUrl, OrganizationType.COMPANY, profileStatus, role);
		this.companyType = companyType;
		this.collaborationType = collaborationType;
		this.sponsorshipContent = sponsorshipContent;
	}
}
