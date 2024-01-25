package com.sponus.sponusbe.domain.announcement.entity;

import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementCategory;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementStatus;
import com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementType;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.global.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "announcement")
public class Announcement extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "announcement_id")
	private Long id;

	@Column(name = "announcement_title", nullable = false)
	private String title;

	@Column(name = "announcement_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private AnnouncementType type;

	@Column(name = "announcement_category", nullable = false)
	@Enumerated(EnumType.STRING)
	private AnnouncementCategory category;

	@Column(name = "announcement_content", nullable = false)
	private String content;

	@Column(name = "announcement_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private AnnouncementStatus status;

	@Column(name = "view_count", nullable = false)
	private long viewCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organization_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Organization writer;

	public void increaseViewCount() {
		this.viewCount++;
	}
}
