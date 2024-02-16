package com.sponus.sponusbe.domain.payment.dto;

public record PaymentRequest(
	String impUid,
	String merchantUid,
	Long proposeId
) {
}

