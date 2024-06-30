package com.sponus.sponusbe.domain.propose.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.propose.repository.ProposeRepository;
import com.sponus.sponusbe.domain.organization.dto.request.PageCondition;
import com.sponus.sponusbe.domain.organization.dto.response.PageResponse;
import com.sponus.sponusbe.domain.propose.dto.response.ProposeGetResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProposeQueryService {

	private final ProposeRepository proposeRepository;

	public PageResponse<ProposeGetResponse> getSendPropose(Organization organization, PageCondition pageCondition) {
		Pageable pageable = PageRequest.of(pageCondition.getPage() - 1, pageCondition.getSize());
		List<ProposeGetResponse> organizations = proposeRepository.findByOrganizationOrderByCreatedAtDesc(
				organization, pageable)
			.stream()
			.map(ProposeGetResponse::from)
			.toList();

		return PageResponse.of(
			PageableExecutionUtils.getPage(organizations, pageable,
				() -> proposeRepository.countByOrganization(organization)));
	}

	public PageResponse<ProposeGetResponse> getReceivedPropose(Organization organization, PageCondition pageCondition) {
		Pageable pageable = PageRequest.of(pageCondition.getPage() - 1, pageCondition.getSize());
		List<ProposeGetResponse> organizations = proposeRepository.findByTargetAndOrganizationOrderByCreatedAtDesc(
				organization, organization, pageable)
			.map(ProposeGetResponse::from)
			.toList();

		return PageResponse.of(
			PageableExecutionUtils.getPage(organizations, pageable,
				() -> proposeRepository.countByTargetAndOrganization(organization, organization)));
	}
}
