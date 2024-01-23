package com.sponus.sponusbe.domain.propose.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.propose.controller.dto.request.ProposeGetCondition;
import com.sponus.sponusbe.domain.propose.controller.dto.response.ProposeDetailGetResponse;
import com.sponus.sponusbe.domain.propose.controller.dto.response.ProposeSummaryGetResponse;
import com.sponus.sponusbe.domain.propose.entity.Propose;
import com.sponus.sponusbe.domain.propose.exception.ProposeErrorCode;
import com.sponus.sponusbe.domain.propose.exception.ProposeException;
import com.sponus.sponusbe.domain.propose.repository.ProposeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProposeQueryService {

	private final ProposeRepository proposeRepository;

	public List<ProposeSummaryGetResponse> getProposes(Organization organization, ProposeGetCondition condition) {
		// TODO : 추후에 QueryDSL 이용
		List<ProposeSummaryGetResponse> response;
		if (condition.isSentPropose()) {
			// 내가 보낸 제안은 그냥 반환
			response = proposeRepository.findSentPropose(organization.getId()).stream()
				.map(ProposeSummaryGetResponse::from)
				.toList();
		} else {
			// 내가 받은 제안은 공고 id 별로 보여줘야하고, 공고 id가 없으면 안됨
			if (condition.announcementId() == null) {
				throw new ProposeException(ProposeErrorCode.ANNOUNCEMENT_ID_IS_REQUIRED);
			}
			response = proposeRepository.findReceivedProposeWithAnnouncementId(
					organization.getId(),
					condition.announcementId())
				.stream()
				.map(ProposeSummaryGetResponse::from)
				.toList();
		}
		return response;
	}

	public ProposeDetailGetResponse getProposeDetail(Long proposeId) {
		final Propose propose = proposeRepository.findById(proposeId)
			.orElseThrow(() -> new ProposeException(ProposeErrorCode.PROPOSE_NOT_FOUND));
		return ProposeDetailGetResponse.from(propose);
	}
}
