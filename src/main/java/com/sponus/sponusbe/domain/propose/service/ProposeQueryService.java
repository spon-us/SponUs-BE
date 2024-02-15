package com.sponus.sponusbe.domain.propose.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.propose.dto.response.DateGroupedProposeResponse;
import com.sponus.sponusbe.domain.propose.dto.response.ProposeSummaryGetResponse;
import com.sponus.sponusbe.domain.propose.repository.ProposeCustomRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProposeQueryService {

	private final ProposeCustomRepository proposeCustomRepository;

	public List<DateGroupedProposeResponse> getSentProposes(Organization organization) {
		return DateGroupedProposeResponse.from(
			proposeCustomRepository.findSentPropose(organization.getId()).stream()
				.map(ProposeSummaryGetResponse::from)
				.toList());
	}

	public List<DateGroupedProposeResponse> getReceivedProposes(Organization organization, Long announcementId) {
		return DateGroupedProposeResponse.from(
			proposeCustomRepository.findReceivedProposeWithAnnouncementId(organization.getId(), announcementId).stream()
				.map(ProposeSummaryGetResponse::from)
				.toList());
	}
}
