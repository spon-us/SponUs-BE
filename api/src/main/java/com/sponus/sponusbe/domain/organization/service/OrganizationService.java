package com.sponus.sponusbe.domain.organization.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sponus.coredomain.domain.bookmark.repository.BookmarkRepository;
import com.sponus.coredomain.domain.organization.Club;
import com.sponus.coredomain.domain.organization.Company;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.coredomain.domain.organization.enums.ProfileStatus;
import com.sponus.coredomain.domain.organization.repository.OrganizationRepository;
import com.sponus.coreinfraredis.entity.SearchHistory;
import com.sponus.coreinfraredis.repository.SearchHistoryRepository;
import com.sponus.coreinfras3.S3Service;
import com.sponus.sponusbe.domain.organization.company.dto.OrganizationGetResponse;
import com.sponus.sponusbe.domain.organization.dto.request.OrganizationCreateRequest;
import com.sponus.sponusbe.domain.organization.dto.request.PageCondition;
import com.sponus.sponusbe.domain.organization.dto.response.OrganizationImageUploadResponse;
import com.sponus.sponusbe.domain.organization.dto.response.OrganizationSearchResponse;
import com.sponus.sponusbe.domain.organization.dto.response.PageResponse;
import com.sponus.sponusbe.domain.organization.exception.OrganizationErrorCode;
import com.sponus.sponusbe.domain.organization.exception.OrganizationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrganizationService {
	private final OrganizationRepository organizationRepository;
	private final BookmarkRepository bookmarkRepository;
	private final S3Service s3Service;
	private final PasswordEncoder passwordEncoder;
	private final SearchHistoryRepository searchHistoryRepository;

	public Long createOrganization(OrganizationCreateRequest request) {
		Organization organization;
		if (request.organizationType() == OrganizationType.COMPANY)
			organization = new Company(request.name(), request.email(), passwordEncoder.encode(request.password()));
		else
			organization = new Club(request.name(), request.email(), passwordEncoder.encode(request.password()));
		return organizationRepository.save(organization).getId();
	}

	public OrganizationImageUploadResponse uploadProfileImage(Long organizationId, MultipartFile file) {
		// TODO : 이미지 업로드 시, S3에 단체 ID를 태그 정보로 넣기
		Organization organization = findOrganizationById(organizationId);
		String imageUrl = s3Service.uploadImage(file);
		return new OrganizationImageUploadResponse(imageUrl);
	}

	public Boolean verifyName(String name) {
		return organizationRepository.existsByName(name);
	}

	public void deleteOrganization(Long organizationId) {
		Organization organization = findOrganizationById(organizationId);
		organization.delete();
	}

	private Organization findOrganizationById(Long organizationId) {
		return organizationRepository.findById(organizationId)
			.orElseThrow(() -> new OrganizationException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND));
	}

	public PageResponse<OrganizationGetResponse> getOrganizations(
		Organization authOrganization,
		PageCondition pageCondition,
		OrganizationType organizationType) {
		// TODO: FETCH JOIN으로 변경
		Set<Long> bookmarkedOrganizationIds = bookmarkRepository.findByOrganization(authOrganization).stream()
			.map((bookmark) -> bookmark.getTarget().getId())
			.collect(Collectors.toSet());
		Pageable pageable = PageRequest.of(pageCondition.getPage() - 1, pageCondition.getSize());
		List<OrganizationGetResponse> organizations = organizationRepository.findOrganizations(
				organizationType, pageable)
			.stream()
			.map(organization ->
				OrganizationGetResponse.of(organization, bookmarkedOrganizationIds.contains(organization.getId())))
			.toList();

		return PageResponse.of(
			PageableExecutionUtils.getPage(organizations, pageable,
				() -> organizationRepository.countByOrganizationType(organizationType)));
	}

	public PageResponse<OrganizationSearchResponse> searchOrganizations(PageCondition pageCondition, String keyword,
		Long organizationId) {

		Pageable pageable = PageRequest.of(pageCondition.getPage() - 1, pageCondition.getSize());
		List<OrganizationSearchResponse> organizations = organizationRepository.findByNameContains(
				keyword, pageable)
			.stream()
			.filter(organization -> organization.getProfileStatus().equals(ProfileStatus.ACTIVE))
			.filter(organization -> !organization.getId().equals(organizationId))
			.map(OrganizationSearchResponse::of)
			.toList();

		return PageResponse.of(
			PageableExecutionUtils.getPage(organizations, pageable,
				() -> organizationRepository.countByNameContains(keyword)));
	}

	public void createSearchHistory(Long organizationId, String keyword) {
		SearchHistory searchHistory = findSearchHistory(organizationId);

		// 기존 값이 존재할 경우 제거 후 추가
		if (searchHistory.getKeywords().contains(keyword)) {
			searchHistory.getKeywords().remove(keyword);
		}

		searchHistory.getKeywords().add(keyword);
		searchHistoryRepository.save(searchHistory);
	}

	public List<String> getSearchHistory(Long organizationId) {
		Set<String> searchHistory = findSearchHistory(organizationId).getKeywords();

		List<String> searchHistoryList = new ArrayList<>(searchHistory);
		searchHistoryList.removeIf(String::isEmpty);

		if (!searchHistoryList.isEmpty()) {
			Collections.reverse(searchHistoryList);
		}

		return searchHistoryList;
	}

	public SearchHistory findSearchHistory(Long organizationId) {
		return searchHistoryRepository.findById(organizationId).orElseGet(() -> {
			SearchHistory newSearchHistory = SearchHistory.builder()
				.organizationId(organizationId)
				.build();
			return searchHistoryRepository.save(newSearchHistory);
		});
	}

	public void deleteSearchKeyword(Long organizationId, String keyword) {
		// TODO 검색어 에러 처리
		SearchHistory searchHistory = searchHistoryRepository.findById(organizationId)
			.orElseThrow(() -> new OrganizationException(OrganizationErrorCode.ORGANIZATION_ERROR));
		searchHistory.getKeywords().remove(keyword);
		searchHistoryRepository.save(searchHistory);
	}

	public void deleteAllSearchKeyword(Long organizationId) {
		SearchHistory searchHistory = searchHistoryRepository.findById(organizationId)
			.orElseThrow(() -> new OrganizationException(OrganizationErrorCode.ORGANIZATION_ERROR));
		searchHistory.getKeywords().clear();
		searchHistoryRepository.save(searchHistory);
	}
}
