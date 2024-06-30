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
import com.sponus.sponusbe.domain.propose.dto.response.ReceiveProposeGetResponse;
import com.sponus.sponusbe.domain.propose.dto.response.SendProposeGetResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProposeQueryService {

	private final ProposeRepository proposeRepository;

	public PageResponse<SendProposeGetResponse> getSendPropose(Organization organization, PageCondition pageCondition) {
		Pageable pageable = PageRequest.of(pageCondition.getPage() - 1, pageCondition.getSize());
		List<SendProposeGetResponse> organizations = proposeRepository.findByOrganizationOrderByCreatedAtDesc(
				organization, pageable)
			.stream()
			.map(SendProposeGetResponse::from)
			.toList();

		return PageResponse.of(
			PageableExecutionUtils.getPage(organizations, pageable,
				() -> proposeRepository.countByOrganization(organization)));
	}

	public PageResponse<ReceiveProposeGetResponse> getReceivedPropose(Organization organization,
		PageCondition pageCondition) {
		Pageable pageable = PageRequest.of(pageCondition.getPage() - 1, pageCondition.getSize());
		List<ReceiveProposeGetResponse> receivedProposes = proposeRepository.findByTargetAndOrganizationOrderByCreatedAtDesc(
				organization, organization, pageable)
			.stream()
			.map(ReceiveProposeGetResponse::from)
			.toList();

		return PageResponse.of(
			PageableExecutionUtils.getPage(receivedProposes, pageable,
				() -> proposeRepository.countByTargetAndOrganization(organization, organization)));
	}
}
