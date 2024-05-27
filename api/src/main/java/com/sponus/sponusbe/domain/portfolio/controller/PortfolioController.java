package com.sponus.sponusbe.domain.portfolio.controller;

import static com.sponus.sponusbe.global.enums.ApiPath.*;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coreinfras3.S3Service;
import com.sponus.sponusbe.domain.portfolio.dto.PortfolioCreateRequest;
import com.sponus.sponusbe.domain.portfolio.dto.PortfolioCreateResponse;
import com.sponus.sponusbe.domain.portfolio.dto.PortfolioGetResponse;
import com.sponus.sponusbe.domain.portfolio.dto.PortfolioImageCreateResponse;
import com.sponus.sponusbe.domain.portfolio.dto.PortfolioUpdateRequest;
import com.sponus.sponusbe.domain.portfolio.service.PortfolioService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(PORTFOLIO_URI)
@RestController
public class PortfolioController {
	private final PortfolioService portfolioService;
	private final S3Service s3Service;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ApiResponse<PortfolioCreateResponse> createPortfolio(
		@RequestPart(value = "request") @Valid PortfolioCreateRequest request,
		@RequestPart(value = "files", required = false) List<MultipartFile> images) {
		PortfolioCreateResponse response = portfolioService.createPortfolio(request, images);
		//TODO: portfolioImageIds만 리턴하는게 아니라, filename, id key-value로 return.
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
	public ApiResponse<Void> updatePortfolio(@PathVariable Long portfolioId,
		@RequestBody PortfolioUpdateRequest request) {
		portfolioService.updatePortfolio(portfolioId, request);
		return ApiResponse.onSuccess(null);
	}

	@PostMapping("/{portfolioId}/portfolio-images")
	public ApiResponse<PortfolioImageCreateResponse> uploadPortfolioImages(
		@PathVariable Long portfolioId,
		@RequestPart(value = "files") @Valid @NotEmpty List<@NotNull MultipartFile> images) {
		PortfolioImageCreateResponse portfolioImageCreateResponse = portfolioService.uploadPortfolioImages(portfolioId,
			images);

		return ApiResponse.onSuccess(portfolioImageCreateResponse);
	}
}
