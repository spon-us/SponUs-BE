package com.sponus.coredomain.domain.organization;

import com.sponus.coredomain.domain.common.BaseEntity;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.coredomain.domain.organization.enums.ProfileStatus;
import com.sponus.coredomain.domain.organization.enums.Role;

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

	@Column(name = "organization_description")
	private String description;

	@Column(name = "organization_image_url")
	private String imageUrl;

	@Column(name = "bookmark_count")
	private int bookmarkCount;

	@Column(name = "view_count")
	private int viewCount;

	@Enumerated(EnumType.STRING)
	@Column(name = "organization_type", nullable = false)
	private OrganizationType organizationType;

	@Enumerated(EnumType.STRING)
	@Column(name = "profile_status", nullable = false)
	private ProfileStatus profileStatus;

	@Column(name = "is_notifications_allowed")
	private boolean isNotificationsAllowed;

	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private Role role;
}
