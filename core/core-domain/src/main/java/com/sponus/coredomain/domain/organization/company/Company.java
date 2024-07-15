package com.sponus.coredomain.domain.organization.company;

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
@Table(name = "company")
@DiscriminatorValue("COMPANY")
public class Company extends Organization {

	@Column(name = "sponsorship_content")
	private String sponsorshipContent;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@Column(name = "company_type")
	private Set<CompanyType> companyTypes = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@Column(name = "collaboration_type")
	private Set<CollaborationType> collaborationTypes = new HashSet<>();

	// 초기 생성 시 사용
	public Company(String name, String email, String password) {
		super(name, email, password, null, null, OrganizationType.COMPANY, ProfileStatus.INACTIVE, Role.GUEST);
	}

	@Override
	public List<String> getSubTypeNames() {
		return companyTypes.stream().map(type -> type.getType().name()).toList();
	}

	public List<String> getCollaborationTypeNames() {
		return collaborationTypes.stream().map(type -> type.getType().name()).toList();
	}

	// 프로필 업데이트 시 사용
	public void updateInfo(
		String name,
		String description,
		String imageUrl,
		ProfileStatus profileStatus,
		String sponsorshipContent
	) {
		super.updateInfo(name, description, imageUrl, profileStatus);
		this.sponsorshipContent = sponsorshipContent;
	}

}
