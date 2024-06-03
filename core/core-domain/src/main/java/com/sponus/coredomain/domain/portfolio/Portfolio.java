package com.sponus.coredomain.domain.portfolio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.sponus.coredomain.domain.organization.Club;

import jakarta.persistence.CascadeType;
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
import lombok.Setter;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "portfolio")
public class Portfolio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "portfolio_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "club_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Club club;
	
	@Column(name = "portfolio_start_date")
	private LocalDate startDate;

	@Column(name = "portfolio_end_date")
	private LocalDate endDate;

	@Column(name = "portfolio_description")
	private String description;

	@Setter
	@Builder.Default
	@OneToMany(cascade = {CascadeType.ALL})
	private List<PortfolioImage> portfolioImages = new ArrayList<>();

	public void addPortfolioImage(PortfolioImage portfolioImage) {
		portfolioImage.setPortfolio(this);
		this.portfolioImages.add(portfolioImage);
	}

	public void update(LocalDate startDate, LocalDate endDate, String description) {
		this.startDate = startDate == null ? this.startDate : startDate;
		this.endDate = endDate == null ? this.endDate : endDate;
		this.description = description == null ? this.description : description;
	}
}
