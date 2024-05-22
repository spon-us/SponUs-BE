package com.sponus.sponusbe.domain.portfolio.dto;

public record PortfolioImageGetResponse(
	Long portfolioImageId,
	String url,
	Integer order
) {
}
