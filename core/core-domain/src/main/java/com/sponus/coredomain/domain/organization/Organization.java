package com.sponus.coredomain.domain.organization;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.sponus.coredomain.domain.common.BaseEntity;
import com.sponus.coredomain.domain.organization.enums.OrganizationStatus;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.coredomain.domain.organization.enums.SuborganizationType;
import com.sponus.coredomain.domain.tag.Tag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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

	@Column(name = "organization_location")
	private String location;

	@Column(name = "organization_description")
	private String description;

	@Column(name = "organization_image_url")
	private String imageUrl;

	@Column(name = "organization_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private OrganizationType organizationType;

	@Column(name = "suborganization_type")
	@Enumerated(EnumType.STRING)
	private SuborganizationType suborganizationType;

	@Column(name = "manager_name")
	private String managerName;

	@Column(name = "manager_position")
	private String managerPosition;

	@Column(name = "manager_email")
	private String managerEmail;

	@Column(name = "manager_phone")
	private String managerPhone;

	@Column(name = "manager_available_day")
	private String managerAvailableDay;

	@Column(name = "manager_available_hour")
	private String managerAvailableHour;

	@Column(name = "manager_contact_preference")
	private String managerContactPreference;

	@Column(name = "notifications_enabled")
	@ColumnDefault("false")
	private boolean notificationsEnabled;

	@Column(name = "organization_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private OrganizationStatus organizationStatus;

	@Builder.Default
	@OneToMany(mappedBy = "organization", fetch = FetchType.EAGER)
	private List<Tag> tags = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "organization", fetch = FetchType.EAGER)
	private List<OrganizationLink> organizationLinks = new ArrayList<>();

	public boolean isStudentOrganization() {
		return this.organizationType == OrganizationType.STUDENT;
	}

	public void update(String name, String email, String password, String location, String description,
		OrganizationType organizationType, SuborganizationType suborganizationType, String managerName,
		String managerPosition, String managerEmail, String managerPhone, String managerAvailableDay,
		String managerAvailableHour, String managerContactPreference
	) {
		this.name = name == null ? this.name : name;
		this.email = email == null ? this.email : email;
		this.password = password == null ? this.password : password;
		this.location = location == null ? this.location : location;
		this.description = description == null ? this.description : description;
		this.organizationType = organizationType == null ? this.organizationType : organizationType;
		this.suborganizationType =
			suborganizationType == null ? this.suborganizationType : suborganizationType;
		this.managerName = managerName == null ? this.managerName : managerName;
		this.managerPosition = managerPosition == null ? this.managerPosition : managerPosition;
		this.managerEmail = managerEmail == null ? this.managerEmail : managerEmail;
		this.managerPhone = managerPhone == null ? this.managerPhone : managerPhone;
		this.managerAvailableDay =
			managerAvailableDay == null ? this.managerAvailableDay : managerAvailableDay;
		this.managerAvailableHour =
			managerAvailableHour == null ? this.managerAvailableHour : managerAvailableHour;
		this.managerContactPreference = managerContactPreference == null ? this.managerContactPreference :
			managerContactPreference;
	}

	public void updateImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void deactivate() {
		this.organizationStatus = OrganizationStatus.INACTIVE;
	}

	public void activate() {
		this.organizationStatus = OrganizationStatus.ACTIVE;
	}
}
