package com.sponus.sponusbe.domain.propose.entity;

public enum ProposeStatus {
	// 제안을 보낸 측: PENDING, CANCELED(임시)
	// 제안을 받은 측: ACCEPTED, REJECTED, SUSPENDED, ACCEPTED_AND_PAID
	PENDING, CANCELED, ACCEPTED, REJECTED, SUSPENDED, ACCEPTED_AND_PAID;
}
