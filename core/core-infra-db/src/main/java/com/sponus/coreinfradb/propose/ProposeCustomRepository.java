package com.sponus.coreinfradb.propose;

import java.util.List;

import com.sponus.coredomain.domain.propose.Propose;

public interface ProposeCustomRepository {

	List<Propose> findSentPropose(Long id);

	List<Propose> findReceivedProposeWithAnnouncementId(Long organizationId, Long announcementId);

}
