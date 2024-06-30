package com.sponus.coredomain.domain.organization;

import java.util.ArrayList;
import java.util.List;

import com.sponus.coredomain.domain.bookmark.Bookmark;
import com.sponus.coredomain.domain.common.BaseEntity;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.coredomain.domain.organization.enums.ProfileStatus;
import com.sponus.coredomain.domain.organization.enums.Role;
import com.sponus.coredomain.domain.portfolio.PortfolioImage;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "organization")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
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

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@Builder.Default
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Bookmark> bookmarks = new ArrayList<>();

	protected Organization(
		String name,
		String email,
		String password,
		String description,
		String imageUrl,
		OrganizationType organizationType,
		ProfileStatus profileStatus,
		Role role) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.description = description;
		this.imageUrl = imageUrl;
		this.bookmarkCount = 0;
		this.viewCount = 0;
		this.organizationType = organizationType;
		this.profileStatus = profileStatus;
		this.isNotificationsAllowed = false;
		this.role = role;
		this.isDeleted = false;
	}

	protected void updateInfo(
		String name,
		String description,
		String imageUrl,
		ProfileStatus profileStatus) {
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
		this.profileStatus = profileStatus;
		// 게스트인 경우 유저로 업데이트
		this.role = this.role == Role.GUEST ? Role.USER : this.role;
	}

	public void delete() {
		this.isDeleted = true;
	}

	public boolean isClub() {
		return this.organizationType == OrganizationType.CLUB;
	}

	public boolean isCompany() {
		return this.organizationType == OrganizationType.COMPANY;
	}

	public String getSubType() {
		return null;
	}
}
