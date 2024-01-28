package com.sponus.sponusbe.domain.propose.dto.request;

import jakarta.validation.constraints.NotNull;

public record ProposeGetCondition(
	@NotNull
	ProposeType proposeType,
	Long announcementId
) {
	public boolean isSentPropose() {
		return proposeType == ProposeType.SEND;
	}

	public boolean isReceivedPropose() {
		return proposeType == ProposeType.RECEIVED;
	}
}