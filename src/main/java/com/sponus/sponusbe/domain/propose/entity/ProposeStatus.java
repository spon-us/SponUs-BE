package com.sponus.sponusbe.domain.propose.entity;

import com.sponus.sponusbe.domain.propose.exception.ProposeErrorCode;
import com.sponus.sponusbe.domain.propose.exception.ProposeException;

public enum ProposeStatus {
	// 제안을 보낸 측: PENDING
	// 제안을 받은 측: VIEWED, ACCEPTED, REJECTED, SUSPENDED, ACCEPTED_AND_PAID
	PENDING, VIEWED, ACCEPTED, REJECTED, SUSPENDED, ACCEPTED_AND_PAID;

	public static ProposeStatus of(String input) {
		try {
			return ProposeStatus.valueOf(input.toUpperCase());
		} catch (Exception e) {
			throw new ProposeException(ProposeErrorCode.INVALID_PROPOSE_STATUS);
		}
	}
}
