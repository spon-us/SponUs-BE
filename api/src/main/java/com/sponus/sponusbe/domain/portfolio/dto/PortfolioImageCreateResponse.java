package com.sponus.sponusbe.domain.portfolio.dto;

import java.util.List;

public record PortfolioImageCreateResponse(
	Long portfolioId,
	List<PortfolioImageGetResponse> portfolioImageGetResponses
) {
}
