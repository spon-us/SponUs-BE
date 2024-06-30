package com.sponus.sponusbe.domain.propose.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.propose.repository.ProposeRepository;
import com.sponus.sponusbe.domain.propose.dto.response.ProposeGetResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProposeQueryService {

	private final ProposeRepository proposeRepository;

	public List<ProposeGetResponse> getSendPropose(Organization organization) {
		return proposeRepository.findByOrganizationOrderByCreatedAtDesc(organization)
			.stream()
			.map(ProposeGetResponse::from)
			.toList();
	}
}
