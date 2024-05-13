package com.sponus.coredomain.domain.portfolio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "portfolio")
public class Portfolio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "portfolio_id")
	private Long id;

	@Column(name = "portfolio_start_date")
	private LocalDate startDate;

	@Column(name = "portfolio_end_date")
	private LocalDate endDate;

	@Column(name = "portfolio_description")
	private String description;

	@Builder.Default
	@OneToMany
	private List<PortfolioImage> portfolioImages = new ArrayList<>();
}
