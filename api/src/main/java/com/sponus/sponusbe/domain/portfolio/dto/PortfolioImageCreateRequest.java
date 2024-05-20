package com.sponus.sponusbe.domain.portfolio.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public record PortfolioImageCreateRequest(
	List<MultipartFile> images
) {
}
