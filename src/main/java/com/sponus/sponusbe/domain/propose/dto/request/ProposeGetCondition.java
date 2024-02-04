package com.sponus.sponusbe.domain.propose.dto.request;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;

public record ProposeGetCondition(
	@NotNull
	ProposeSearchType proposeSearchType,
	Long announcementId
) {
	@Hidden
	public boolean isSentPropose() {
		return proposeSearchType == ProposeSearchType.SEND;
	}

	@Hidden
	public boolean isReceivedPropose() {
		return proposeSearchType == ProposeSearchType.RECEIVED;
	}
}
