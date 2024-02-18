package com.sponus.sponusbe.domain.propose.repository;

import java.util.List;

import com.sponus.sponusbe.domain.propose.entity.Propose;

public interface ProposeCustomRepository {

	List<Propose> findSentPropose(Long id);

	List<Propose> findReceivedProposeWithAnnouncementId(Long organizationId, Long announcementId);

}
