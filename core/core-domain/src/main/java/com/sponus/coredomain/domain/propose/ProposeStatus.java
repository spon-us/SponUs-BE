package com.sponus.coredomain.domain.propose;

public enum ProposeStatus {
	// 제안을 보낸 측: PENDING
	// 제안을 받은 측: VIEWED, ACCEPTED, REJECTED, SUSPENDED, PAID
	PENDING, VIEWED, ACCEPTED, REJECTED, SUSPENDED, PAID, COMPLETED;

	public static ProposeStatus of(String input) {
		return ProposeStatus.valueOf(input.toUpperCase());
	}
}
