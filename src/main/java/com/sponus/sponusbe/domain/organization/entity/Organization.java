package com.sponus.sponusbe.domain.organization.entity;

import java.util.ArrayList;
import java.util.List;

import com.sponus.sponusbe.domain.organization.entity.enums.OrganizationStatus;
import com.sponus.sponusbe.domain.organization.entity.enums.OrganizationType;
import com.sponus.sponusbe.domain.organization.entity.enums.SuborganizationType;
import com.sponus.sponusbe.global.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "organization")
public class Organization extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "organization_id")
	private Long id;

	@Column(name = "organization_name", nullable = false)
	private String name;

	@Column(name = "organization_email", nullable = false, unique = true)
	private String email;

	@Column(name = "organization_password", nullable = false)
	private String password;

	@Column(name = "organization_location", nullable = false)
	private String location;

	@Column(name = "organization_description")
	private String description;

	@Column(name = "organization_image_url")
	private String imageUrl;

	@Column(name = "organization_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private OrganizationType organizationType;

	@Column(name = "suborganization_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private SuborganizationType suborganizationType;

	@Column(name = "manager_name", nullable = false)
	private String managerName;

	@Column(name = "manager_position", nullable = false)
	private String managerPosition;

	@Column(name = "manager_email", nullable = false)
	private String managerEmail;

	@Column(name = "manager_phone", nullable = false)
	private String managerPhone;

	@Column(name = "manager_available_day", nullable = false)
	private String managerAvailableDay;

	@Column(name = "manager_available_hour", nullable = false)
	private String managerAvailableHour;

	@Column(name = "manager_contact_preference", nullable = false)
	private String managerContactPreference;

	@Column(name = "organization_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private OrganizationStatus organizationStatus;

	@Builder.Default
	@OneToMany(mappedBy = "organization")
	private List<OrganizationTag> organizationTags = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "organization")
	private List<OrganizationLink> organizationLinks = new ArrayList<>();

	public boolean isStudentOrganization() {
		return this.organizationType == OrganizationType.STUDENT;
	}
}
