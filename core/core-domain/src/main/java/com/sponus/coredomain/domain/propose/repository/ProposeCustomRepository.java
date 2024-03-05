package com.sponus.coredomain.domain.propose.repository;

import java.util.List;

import com.sponus.coredomain.domain.propose.Propose;

public interface ProposeCustomRepository {

	List<Propose> findSentPropose(Long id);

	List<Propose> findReceivedProposeWithAnnouncementId(Long organizationId, Long announcementId);

}
