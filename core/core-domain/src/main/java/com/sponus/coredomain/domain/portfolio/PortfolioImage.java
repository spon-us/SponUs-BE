package com.sponus.coredomain.domain.portfolio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "portfolio_image")
public class PortfolioImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "portfolio_image_id")
	private Long id;

	@Column(name = "portfolio_image_url")
	private String url;

	@Column(name = "portfolio_image_order")
	private Long order;
}
