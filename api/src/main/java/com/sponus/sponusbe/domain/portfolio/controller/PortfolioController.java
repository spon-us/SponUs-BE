package com.sponus.sponusbe.domain.portfolio.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coreinfras3.S3Service;
import com.sponus.sponusbe.domain.portfolio.dto.PortfolioCreateRequest;
import com.sponus.sponusbe.domain.portfolio.dto.PortfolioCreateResponse;
import com.sponus.sponusbe.domain.portfolio.dto.PortfolioGetResponse;
import com.sponus.sponusbe.domain.portfolio.dto.PortfolioImageCreateRequest;
import com.sponus.sponusbe.domain.portfolio.dto.PortfolioImageCreateResponse;
import com.sponus.sponusbe.domain.portfolio.dto.PortfolioUpdateRequest;
import com.sponus.sponusbe.domain.portfolio.service.PortfolioService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v2/portfolio")
@RestController
public class PortfolioController {
	private final PortfolioService portfolioService;
	private final S3Service s3Service;

	@PostMapping
	public ApiResponse<PortfolioCreateResponse> createPortfolio(PortfolioCreateRequest request) {
		PortfolioCreateResponse response = portfolioService.createPortfolio(request);
		return ApiResponse.onSuccess(response);
	}

	@GetMapping("/{portfolioId}")
	public ApiResponse<PortfolioGetResponse> getPortfolio(@PathVariable Long portfolioId) {
		PortfolioGetResponse response = portfolioService.getPortfolio(portfolioId);
		return ApiResponse.onSuccess(response);
	}

	@DeleteMapping("/{portfolioId}")
	public ApiResponse<Void> deletePortfolio(@PathVariable Long portfolioId) {
		portfolioService.deletePortfolio(portfolioId);
		return ApiResponse.onSuccess(null);
	}

	@PatchMapping("/{portfolioId}")
	public ApiResponse<Void> updatePortfolio(@PathVariable Long portfolioId, PortfolioUpdateRequest request) {
		portfolioService.updatePortfolio(portfolioId, request);
		return ApiResponse.onSuccess(null);
	}

	@PostMapping("/{portfolioId}/portfolio-images")
	public ApiResponse<PortfolioImageCreateResponse> uploadPortfolioImages(
		@PathVariable Long portfolioId,
		PortfolioImageCreateRequest request) {
		PortfolioImageCreateResponse portfolioImageCreateResponse = portfolioService.uploadPortfolioImages(portfolioId,
			request);

		return ApiResponse.onSuccess(portfolioImageCreateResponse);
	}
}
