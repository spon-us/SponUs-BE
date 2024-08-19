package com.sponus.sponusbe.domain.portfolio.dto;

import java.time.LocalDate;
import java.util.List;

import com.sponus.coredomain.domain.portfolio.Portfolio;

public record PortfolioGetResponse(
	Long portfolioId,
	Long clubId,
	LocalDate startDate,
	LocalDate endDate,
	String title,
	String description,
	List<PortfolioImageGetResponse> portfolioImageGetResponses
) {
	public static PortfolioGetResponse from(Portfolio portfolio,
		List<PortfolioImageGetResponse> portfolioImageGetResponses) {
		return new PortfolioGetResponse(
			portfolio.getId(),
			portfolio.getClub().getId(),
			portfolio.getStartDate(),
			portfolio.getEndDate(),
			portfolio.getTitle(),
			portfolio.getDescription(),
			portfolioImageGetResponses
		);
	}
}
