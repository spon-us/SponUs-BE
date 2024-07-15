package com.sponus.coredomain.domain.organization.club;

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
@Table(name = "club_type")
public class ClubType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "club_type_id")
	private Long id;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "type")
	private ClubTypeEnum type;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "club_id")
	private Club club;

	public ClubType(ClubTypeEnum clubTypeEnum) {
		this.type = clubTypeEnum;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		ClubType other = (ClubType)obj;
		return Objects.equals(this.type, other.getType());
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}

	public void updateClub(Club club) {
		this.club = club;
		club.getClubTypes().add(this);
	}
}
