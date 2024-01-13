package com.sponus.sponusbe.domain.organization.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sponus.sponusbe.domain.organization.enums.OrganizationStatus;
import com.sponus.sponusbe.domain.organization.enums.OrganizationType;
import com.sponus.sponusbe.domain.organization.enums.SuborganizationType;
import com.sponus.sponusbe.global.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "organization")
public class Organization extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "organization_id")
	private Long id;

	@Column(name = "organization_name")
	@JsonProperty("name")
	@NonNull
	private String name;

	@Column(name = "organization_email", unique = true)
	@JsonProperty("email")
	@NonNull
	private String email;

	@Column(name = "organization_password")
	@JsonProperty("password")
	@NonNull
	private String password;

	@Column(name = "organization_location")
	@JsonProperty("location")
	@NonNull
	private String location;

	@Column(name = "organization_description")
	@JsonProperty("description")
	private String description;

	@Column(name = "organization_image_url")
	@JsonProperty("image_url")
	private String imageUrl;

	@Column(name = "organization_type")
	@Enumerated(EnumType.STRING)
	@JsonProperty("type")
	@NonNull
	private OrganizationType organizationType;

	@Column(name = "suborganization_type")
	@Enumerated(EnumType.STRING)
	@JsonProperty("subtype")
	@NonNull
	private SuborganizationType suborganizationType;

	@Column(name = "manager_name")
	@JsonProperty("manager_name")
	@NonNull
	private String managerName;

	@Column(name = "manager_position")
	@JsonProperty("manager_position")
	@NonNull
	private String managerPosition;

	@Column(name = "manager_email")
	@JsonProperty("manager_email")
	@NonNull
	private String managerEmail;

	@Column(name = "manager_phone")
	@JsonProperty("manager_phone")
	@NonNull
	private String managerPhone;

	@Column(name = "manager_available_day")
	@JsonProperty("manager_available_day")
	@NonNull
	private String managerAvailableDay;

	@Column(name = "manager_available_hour")
	@JsonProperty("manager_available_hour")
	@NonNull
	private String managerAvailableHour;

	@Column(name = "manager_contact_preference")
	@JsonProperty("manager_contact_preference")
	@NonNull
	private String managerContactPreference;

	@Column(name = "organization_status")
	@Enumerated(EnumType.STRING)
	@NonNull
	private OrganizationStatus organizationStatus;
}
