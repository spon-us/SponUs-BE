package com.sponus.sponusbe.domain.propose.entity;

public enum ProposeStatus {
	// 제안을 보낸 측: PENDING
	// 제안을 받은 측: VIEWED, ACCEPTED, REJECTED, SUSPENDED, ACCEPTED_AND_PAID
	PENDING, VIEWED, ACCEPTED, REJECTED, SUSPENDED, ACCEPTED_AND_PAID,
	// 열람됨

}
