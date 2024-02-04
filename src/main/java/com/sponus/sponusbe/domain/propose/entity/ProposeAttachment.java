package com.sponus.sponusbe.domain.propose.entity;

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
@Table(name = "propose_attachment")
public class ProposeAttachment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "attachment_id")
	private Long id;

	@Column(name = "file_name", nullable = false)
	private String name;

	@Column(name = "file_url", nullable = false)
	private String url;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "propose_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Propose propose;

	public void setPropose(Propose propose) {
		if (this.propose != null) {
			this.propose.getProposeAttachments().remove(this);
		}
		this.propose = propose;
		propose.getProposeAttachments().add(this);
	}
}
