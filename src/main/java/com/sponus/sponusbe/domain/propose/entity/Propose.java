package com.sponus.sponusbe.domain.propose.entity;

import java.util.ArrayList;
import java.util.List;

import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.report.entity.Report;

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
public class Propose {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "propose_id")
	private Long id;

	@Column(name = "propose_title", nullable = false)
	private String title;

	@Column(name = "propose_content", nullable = false)
	private String content;

	@Column(name = "propose_status", nullable = false)
	private ProposeStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "announcement_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Announcement announcement;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proposed_organization_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Organization proposedOrganization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proposing_organization_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Organization proposingOrganization;

	// TODO : 추후에 연관관계 제거 -> 그냥 proposingOrganizationType, getStudentOrganization 메서드로 대체하는게 좋을 것 같음
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_organization_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Organization studentOrganization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_organization_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Organization companyOrganization;

	@OneToOne
	@JoinColumn(name = "report_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Report report;

	@Builder.Default
	@OneToMany(mappedBy = "propose")
	private List<ProposeAttachment> proposeAttachments = new ArrayList<>();
}
