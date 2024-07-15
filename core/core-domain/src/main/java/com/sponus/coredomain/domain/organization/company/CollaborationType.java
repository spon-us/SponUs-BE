package com.sponus.coredomain.domain.organization.company;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "collaboration_type")
public class CollaborationType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "collaboration_type_id")
	private Long id;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "type")
	private CollaborationTypeEnum type;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company_id")
	private Company company;

	public CollaborationType(CollaborationTypeEnum type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		CollaborationType other = (CollaborationType)obj;
		return Objects.equals(this.type, other.getType());
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}

	public void updateCompany(Company company) {
		this.company = company;
		company.getCollaborationTypes().add(this);
	}
}
