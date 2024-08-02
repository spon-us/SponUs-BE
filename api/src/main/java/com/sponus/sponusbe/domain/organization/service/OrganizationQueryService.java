package com.sponus.sponusbe.domain.organization.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.coredomain.domain.organization.enums.ProfileStatus;
import com.sponus.coredomain.domain.organization.repository.OrganizationRepository;
import com.sponus.coredomain.domain.organization.repository.querydsl.conditions.OrganizationSearchCondition;
import com.sponus.coreinfraredis.entity.SearchHistory;
import com.sponus.coreinfraredis.repository.SearchHistoryRepository;
import com.sponus.sponusbe.domain.organization.dto.request.PageCondition;
import com.sponus.sponusbe.domain.organization.dto.response.OrganizationGetResponse;
import com.sponus.sponusbe.domain.organization.dto.response.OrganizationSearchResponse;
import com.sponus.sponusbe.domain.organization.dto.response.PageResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrganizationQueryService {

	private final OrganizationRepository organizationRepository;
	private final SearchHistoryRepository searchHistoryRepository;

	public Boolean verifyName(String name) {
		return organizationRepository.existsByName(name);
	}

	public PageResponse<OrganizationGetResponse> getOrganizations(
		Organization authOrganization,
		PageCondition pageCondition,
		OrganizationType organizationType) {
		// TODO: FETCH JOIN으로 변경
		Set<Long> bookmarkedOrganizationIds = authOrganization.getBookmarks()
			.stream()
			.map(bookmark -> bookmark.getTarget().getId())
			.collect(Collectors.toSet());
		Pageable pageable = PageRequest.of(pageCondition.getPage() - 1, pageCondition.getSize());
		List<OrganizationGetResponse> organizations = organizationRepository.findOrganizations(
				organizationType, pageable, authOrganization.getId())
			.stream()
			.map(organization ->
				OrganizationGetResponse.of(organization, bookmarkedOrganizationIds.contains(organization.getId())))
			.toList();

		return PageResponse.of(
			PageableExecutionUtils.getPage(organizations, pageable,
				() -> organizationRepository.countByOrganizationType(organizationType)));
	}

	public PageResponse<OrganizationSearchResponse> searchOrganizations(PageCondition pageCondition, String keyword,
		Organization authOrganization) {

		Set<Long> bookmarkedOrganizationIds = authOrganization.getBookmarks()
			.stream()
			.map(bookmark -> bookmark.getTarget().getId())
			.collect(Collectors.toSet());
		Pageable pageable = PageRequest.of(pageCondition.getPage() - 1, pageCondition.getSize());

		List<OrganizationSearchResponse> organizations = organizationRepository.findByNameContains(
				keyword, pageable)
			.stream()
			.filter(organization -> organization.getProfileStatus().equals(ProfileStatus.ACTIVE))
			.filter(organization -> !organization.getId().equals(authOrganization.getId()))
			.map(organization ->
				OrganizationSearchResponse.of(organization, bookmarkedOrganizationIds.contains(organization.getId())))
			.toList();

		return PageResponse.of(
			PageableExecutionUtils.getPage(organizations, pageable,
				() -> organizationRepository.countByNameContains(keyword)));
	}

	public PageResponse<OrganizationSearchResponse> searchOrganizationsV2(
		PageCondition pageCondition, String keyword,
		Organization authOrganization) {

		OrganizationSearchCondition condition = OrganizationSearchCondition.of(keyword, authOrganization.getId());
		Pageable pageable = PageRequest.of(pageCondition.getPage() - 1, pageCondition.getSize());

		Page<Organization> organizations = organizationRepository.searchOrganizationV2(condition, pageable);

		Set<Long> bookmarkedOrganizationIds = authOrganization.getBookmarks()
			.stream()
			.map(bookmark -> bookmark.getTarget().getId())
			.collect(Collectors.toSet());

		return PageResponse.of(organizations.map(organization ->
			OrganizationSearchResponse.of(organization, bookmarkedOrganizationIds.contains(organization.getId()))));
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

	private SearchHistory findSearchHistory(Long organizationId) {
		return searchHistoryRepository.findById(organizationId).orElseGet(() -> {
			SearchHistory newSearchHistory = SearchHistory.builder()
				.organizationId(organizationId)
				.build();
			return searchHistoryRepository.save(newSearchHistory);
		});
	}

}
