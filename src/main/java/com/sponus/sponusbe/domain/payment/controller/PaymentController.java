package com.sponus.sponusbe.domain.payment.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siot.IamportRestClient.response.Payment;
import com.sponus.sponusbe.auth.annotation.AuthOrganization;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.payment.dto.PaymentRequest;
import com.sponus.sponusbe.domain.payment.service.PaymentService;
import com.sponus.sponusbe.global.common.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping("/api/v1/payments")
	public ApiResponse<Payment> paymentByImpUid(
		@AuthOrganization Organization authOrganization,
		@RequestBody PaymentRequest request) {
		return ApiResponse.onSuccess(paymentService.paymentByImpUid(request, authOrganization));
	}
}
