package com.sponus.sponusbe.domain.propose.entity;

import java.util.ArrayList;
import java.util.List;

import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.report.entity.Report;
import com.sponus.sponusbe.global.common.BaseEntity;

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
import jakarta.persistence.OneToOne;
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
@Table(name = "propose")
public class Propose extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "propose_id")
	private Long id;

	@Column(name = "propose_title", nullable = false)
	private String title;

	@Column(name = "propose_content", nullable = false)
	private String content;

	@Enumerated(EnumType.STRING)
	@Column(name = "propose_status", nullable = false)
	private ProposeStatus status;

	@Column(name = "imp_uid")
	private String impUid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "announcement_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Announcement announcement;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proposed_organization_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Organization proposedOrganization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proposing_organization_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Organization proposingOrganization;

	@OneToOne(mappedBy = "propose", fetch = FetchType.EAGER)
	private Report report;

	@Builder.Default
	@OneToMany(mappedBy = "propose", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProposeAttachment> proposeAttachments = new ArrayList<>();

	public void updateToViewed() {
		if (this.status == ProposeStatus.PENDING)
			this.status = ProposeStatus.VIEWED;
	}

	public void updateInfo(String title, String content) {
		this.title = title == null ? this.title : title;
		this.content = content == null ? this.content : content;
	}

	public void updateStatus(ProposeStatus status) {
		this.status = status == null ? this.status : status;
	}

	public void updateToPaid(String impUid) {
		this.status = ProposeStatus.PAID;
		this.impUid = impUid;
	}

	public boolean isPaid() {
		return this.status == ProposeStatus.PAID;
	}
}
