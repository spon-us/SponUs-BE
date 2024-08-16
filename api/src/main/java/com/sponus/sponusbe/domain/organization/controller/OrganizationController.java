package com.sponus.sponusbe.domain.organization.controller;

import static com.sponus.sponusbe.global.enums.ApiPath.*;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sponus.coredomain.domain.common.ApiResponse;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.enums.OrganizationType;
import com.sponus.coreinfrasecurity.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.organization.club.service.ClubService;
import com.sponus.sponusbe.domain.organization.company.service.CompanyService;
import com.sponus.sponusbe.domain.organization.dto.request.OrganizationCreateRequest;
import com.sponus.sponusbe.domain.organization.dto.request.OrganizationSearchRequest;
import com.sponus.sponusbe.domain.organization.dto.request.PageCondition;
import com.sponus.sponusbe.domain.organization.dto.response.OrganizationGetResponse;
import com.sponus.sponusbe.domain.organization.dto.response.OrganizationImageUploadResponse;
import com.sponus.sponusbe.domain.organization.dto.response.OrganizationSearchResponse;
import com.sponus.sponusbe.domain.organization.dto.response.PageResponse;
import com.sponus.sponusbe.domain.organization.service.OrganizationQueryService;
import com.sponus.sponusbe.domain.organization.service.OrganizationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ORGANIZATION_URI)
@RequiredArgsConstructor
public class OrganizationController {
	private final OrganizationService organizationService;
	private final OrganizationQueryService organizationQueryService;
	private final ClubService clubService;
	private final CompanyService companyService;

	@PostMapping("/join")
	public ApiResponse<Long> join(@RequestBody OrganizationCreateRequest request) {
		return ApiResponse.onSuccess(organizationService.createOrganization(request));
	}

	@GetMapping
	public ApiResponse<PageResponse<OrganizationGetResponse>> getOrganizations(
		@AuthOrganization Organization authOrganization,
		@ModelAttribute @Valid PageCondition pageCondition,
		@ModelAttribute @Valid OrganizationType organizationType) {
		return ApiResponse.onSuccess(
			organizationQueryService.getOrganizations(authOrganization, pageCondition, organizationType));
	}

	@GetMapping("/me")
	public ApiResponse<?> getMyProfile(
		@AuthOrganization Organization authOrganization) {
		if (authOrganization.isClub()) {
			return ApiResponse.onSuccess(clubService.getClub(authOrganization.getId()));
		} else {
			return ApiResponse.onSuccess(companyService.getCompany(authOrganization.getId()));
		}
	}

	@PostMapping(value = "/me/profileImage", consumes = "multipart/form-data")
	public ApiResponse<OrganizationImageUploadResponse> uploadProfileImage(
		@AuthOrganization Organization organization,
		@RequestPart(name = "profileImage") MultipartFile file) {
		return ApiResponse.onSuccess(organizationService.uploadProfileImage(organization.getId(), file));
	}

	@GetMapping("/exists")
	public ApiResponse<Boolean> verifyName(@RequestParam String name) {
		return ApiResponse.onSuccess(organizationQueryService.verifyName(name));
	}

	@DeleteMapping("/{organizationId}")
	public ApiResponse<Void> deleteOrganization(@PathVariable Long organizationId) {
		organizationService.deleteOrganization(organizationId);
		return ApiResponse.onSuccess(null);
	}

	@GetMapping("/search")
	public ApiResponse<PageResponse<OrganizationSearchResponse>> searchOrganization(
		@ModelAttribute @Valid PageCondition pageCondition,
		@RequestParam("keyword") String keyword,
		@AuthOrganization Organization organization
	) {
		return ApiResponse.onSuccess(
			organizationQueryService.searchOrganizationsV2(pageCondition, keyword, organization));
	}

	@DeleteMapping("/search")
	public ApiResponse<Void> deleteAllSearchKeyword(@AuthOrganization Organization organization) {
		organizationService.deleteAllSearchKeyword(organization.getId());
		return ApiResponse.onSuccess(null);
	}

	@PostMapping("/search/keywords")
	public ApiResponse<Void> createSearchHistory(@AuthOrganization Organization organization,
		@RequestBody @Valid OrganizationSearchRequest request) {
		organizationService.createSearchHistory(organization.getId(), request.keyword());
		return ApiResponse.onSuccess(null);
	}

	@GetMapping("/search/keywords")
	public ApiResponse<List<String>> getSearchHistory(@AuthOrganization Organization organization) {
		return ApiResponse.onSuccess(organizationQueryService.getSearchHistory(organization.getId()));
	}

	@DeleteMapping("/search/keywords")
	public ApiResponse<Void> deleteSearchKeyword(
		@AuthOrganization Organization organization,
		@RequestBody @Valid OrganizationSearchRequest request
	) {
		organizationService.deleteSearchKeyword(organization.getId(), request.keyword());
		return ApiResponse.onSuccess(null);
	}
}
