package com.sponus.sponusbe.domain.portfolio.dto;

import java.util.List;

public record PortfolioGetResponse(
	Long portfolioId,
	List<PortfolioImageGetResponse> portfolioImageGetResponses
) {
}
