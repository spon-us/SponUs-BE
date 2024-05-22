package com.sponus.sponusbe.domain.portfolio.service;

import static com.sponus.sponusbe.domain.portfolio.exception.PortfolioErrorCode.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sponus.coredomain.domain.portfolio.Portfolio;
import com.sponus.coredomain.domain.portfolio.PortfolioImage;
import com.sponus.coredomain.domain.portfolio.repository.PortfolioImageRepository;
import com.sponus.coredomain.domain.portfolio.repository.PortfolioRepository;
import com.sponus.coreinfras3.S3Service;
import com.sponus.sponusbe.domain.portfolio.dto.PortfolioCreateRequest;
import com.sponus.sponusbe.domain.portfolio.dto.PortfolioCreateResponse;
import com.sponus.sponusbe.domain.portfolio.dto.PortfolioGetResponse;
import com.sponus.sponusbe.domain.portfolio.dto.PortfolioImageCreateResponse;
import com.sponus.sponusbe.domain.portfolio.dto.PortfolioImageGetResponse;
import com.sponus.sponusbe.domain.portfolio.dto.PortfolioUpdateRequest;
import com.sponus.sponusbe.domain.portfolio.exception.PortfolioException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PortfolioService {
	private final PortfolioRepository portfolioRepository;
	private final PortfolioImageRepository portfolioImageRepository;
	private final S3Service s3Service;

	@Transactional
	public PortfolioCreateResponse createPortfolio(PortfolioCreateRequest request, List<MultipartFile> images) {
		//TODO: save방법 수정. 현재 1(portfolio) + portfolioImage 개수 * 2(portfolioImage테이블 + 매핑테이블) 만금 insert함.
		Portfolio newPortfolio = Portfolio.builder()
			.startDate(request.startDate())
			.endDate(request.endDate())
			.description(request.description())
			.build();

		if (images != null) {
			AtomicInteger orderNo = new AtomicInteger(0);
			images.forEach(image -> {
				String uploadedFileUrl = s3Service.uploadFile(image);
				newPortfolio.addPortfolioImage(
					PortfolioImage.builder()
						.url(uploadedFileUrl)
						.order(orderNo.getAndIncrement())
						.build()
				);
			});
		}

		Portfolio portfolioEntity = portfolioRepository.save(newPortfolio);
		return new PortfolioCreateResponse(
			portfolioEntity.getId(),
			portfolioEntity.getPortfolioImages().stream()
				.map(PortfolioImage::getId)
				.toList()
		);
	}

	public PortfolioGetResponse getPortfolio(long portfolioId) {
		Portfolio portfolio = portfolioRepository.findById(portfolioId)
			.orElseThrow(() -> new PortfolioException(PORTFOLIO_NOT_FOUND));

		List<PortfolioImageGetResponse> portfolioImageGetResponses = portfolio.getPortfolioImages().stream()
			.map(image -> new PortfolioImageGetResponse(image.getId(), image.getUrl(), image.getOrder()))
			.toList();

		return new PortfolioGetResponse(portfolioId, portfolioImageGetResponses);
	}

	@Transactional
	public void deletePortfolio(long portfolioId) {
		if (portfolioRepository.existsById(portfolioId)) {
			portfolioRepository.deleteById(portfolioId);
		} else {
			throw new PortfolioException(PORTFOLIO_NOT_FOUND);
		}
	}

	@Transactional
	public void updatePortfolio(long portfolioId, PortfolioUpdateRequest request) {
		Portfolio portfolio = portfolioRepository.findById(portfolioId)
			.orElseThrow(() -> new PortfolioException(PORTFOLIO_NOT_FOUND));

		portfolio.update(request.startDate(), request.endDate(), request.description());
		portfolioRepository.save(portfolio);
	}

	@Transactional
	public PortfolioImageCreateResponse uploadPortfolioImages(long portfolioId, List<MultipartFile> images) {
		assert images != null && !images.isEmpty();

		Portfolio portfolio = portfolioRepository.findById(portfolioId)
			.orElseThrow(() -> new PortfolioException(PORTFOLIO_NOT_FOUND));
		assert portfolio.getPortfolioImages().isEmpty();

		AtomicInteger orderNo = new AtomicInteger(0);
		images.stream().map(image -> {
			String uploadedFileUrl = s3Service.uploadFile(image);
			PortfolioImage portfolioImage = PortfolioImage.builder()
				.url(uploadedFileUrl)
				.order(orderNo.getAndIncrement())
				.build();
			portfolio.addPortfolioImage(portfolioImage);
			return portfolioImage;
		}).toList();

		Portfolio savedPortfolio = portfolioRepository.save(portfolio);

		List<PortfolioImageGetResponse> portfolioImageGetResponses = savedPortfolio.getPortfolioImages().stream()
			.map(image -> new PortfolioImageGetResponse(image.getId(), image.getUrl(), image.getOrder()))
			.toList();

		return new PortfolioImageCreateResponse(portfolioId, portfolioImageGetResponses);
	}
}
