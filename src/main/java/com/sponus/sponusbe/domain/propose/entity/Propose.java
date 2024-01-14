package com.sponus.sponusbe.domain.propose.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.sponus.sponusbe.domain.announcement.entity.Announcement;
import com.sponus.sponusbe.domain.organization.entity.Organization;

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
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "propose")
public class Propose {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "propose_id")
	private Long id;

	@Column(name = "propose_title")
	private String title;

	@Column(name = "propose_content")
	private String content;

	@Column(name = "propose_status")
	private ProposeStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "announcement_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Announcement announcement;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proposed_organization_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Organization proposedOrganization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_organization_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Organization studentOrganization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_organization_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Organization companyOrganization;

	@Builder.Default
	@OneToMany(mappedBy = "propose")
	private List<ProposeAttachment> proposeAttachments = new ArrayList<>();
}
