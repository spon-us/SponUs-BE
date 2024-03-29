package com.sponus.coredomain.domain.announcement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.sponus.coredomain.domain.announcement.enums.AnnouncementStatus;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementCategory;
import com.sponus.coredomain.domain.announcement.enums.AnnouncementType;
import com.sponus.coredomain.domain.bookmark.Bookmark;
import com.sponus.coredomain.domain.common.BaseEntity;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.propose.Propose;

import jakarta.persistence.CascadeType;
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

	@Builder.Default
	@OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AnnouncementImage> announcementImages = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Bookmark> bookmarks = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Propose> proposes = new ArrayList<>();

	public void increaseViewCount() {
		this.viewCount++;
	}

	public void updateInfo(String title, AnnouncementType type, AnnouncementCategory category, String content) {
		this.title = title == null ? this.title : title;
		this.type = type == null ? this.type : type;
		this.category = category == null ? this.category : category;
		this.content = content == null ? this.content : content;
	}

	public void updateStatus(AnnouncementStatus status) {
		this.status = status;
	}

	public void updateViewCount(long viewCount) {
		this.viewCount = viewCount;
	}

	public boolean isAvailable() {
		return this.status == AnnouncementStatus.OPENED;
	}

	public long getBookmarkSaveCount() {
		return this.bookmarks.size();
	}

	public void setUpdatedAt(LocalDateTime now) {
		this.updatedAt = now;
	}
}
