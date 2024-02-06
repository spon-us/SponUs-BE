package com.sponus.sponusbe.domain.bookmark.entity;

import java.time.LocalDateTime;

import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.global.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
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
@Table(name = "bookmark")
public class Bookmark extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookmark_id")
	private Long id;

	@Column(name = "save_count", nullable = false)
	private long saveCount;

	@Column(name = "view_count", nullable = false)
	private long viewCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organization_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Organization organization;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "announcement_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Announcement announcement;

	public void increaseViewCount() { this.viewCount++; }

	public void increaseSaveCount() { this.saveCount++; }

	public void decreaseSaveCount() {
		if (this.saveCount > 0) {
		this.saveCount--;
	} }
}
