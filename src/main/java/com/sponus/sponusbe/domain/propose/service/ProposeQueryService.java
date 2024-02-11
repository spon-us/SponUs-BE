package com.sponus.sponusbe.domain.propose.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.propose.dto.response.ProposeDetailGetResponse;
import com.sponus.sponusbe.domain.propose.dto.response.ProposeSummaryGetResponse;
import com.sponus.sponusbe.domain.propose.entity.Propose;
import com.sponus.sponusbe.domain.propose.exception.ProposeErrorCode;
import com.sponus.sponusbe.domain.propose.exception.ProposeException;
import com.sponus.sponusbe.domain.propose.repository.ProposeCustomRepository;
import com.sponus.sponusbe.domain.propose.repository.ProposeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProposeQueryService {

	private final ProposeRepository proposeRepository;
	private final ProposeCustomRepository proposeCustomRepository;

	public List<ProposeSummaryGetResponse> getSentProposes(Organization organization) {
		return proposeCustomRepository.findSentPropose(organization.getId()).stream()
			.map(ProposeSummaryGetResponse::from)
			.toList();
	}

	public List<ProposeSummaryGetResponse> getReceivedProposes(
		Organization organization,
		Long announcementId) {
		return proposeCustomRepository.findReceivedProposeWithAnnouncementId(
				organization.getId(),
				announcementId)
			.stream()
			.map(ProposeSummaryGetResponse::from)
			.toList();
	}

	public ProposeDetailGetResponse getProposeDetail(Long proposeId) {
		final Propose propose = proposeRepository.findById(proposeId)
			.orElseThrow(() -> new ProposeException(ProposeErrorCode.PROPOSE_NOT_FOUND));
		return ProposeDetailGetResponse.from(propose);
	}
}
