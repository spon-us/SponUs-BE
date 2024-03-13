package com.sponus.coreinfraportone;

public record PaymentRequest(
	String impUid,
	String merchantUid,
	Long proposeId
) {
}

