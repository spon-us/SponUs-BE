package com.sponus.coredomain.domain.notification;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.sponus.coredomain.domain.common.BaseEntity;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.propose.Propose;

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
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "notification")
public class Notification extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_id")
	private Long id;

	@Column(name = "notification_title", nullable = false)
	private String title;

	@Column(name = "notification_body", nullable = false)
	private String body;

	@Column(name = "notification_is_read", nullable = false)
	@ColumnDefault("false")
	private boolean isRead;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organization_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Organization organization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "propose_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Propose propose;

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public void setPropose(Propose propose) {
		this.propose = propose;
	}

	public void setRead(boolean read) {
		isRead = read;
	}
}
