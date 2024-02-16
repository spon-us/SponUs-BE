package com.sponus.sponusbe.domain.payment.service;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.sponus.sponusbe.domain.organization.entity.Organization;
import com.sponus.sponusbe.domain.payment.dto.PaymentRequest;
import com.sponus.sponusbe.domain.propose.entity.Propose;
import com.sponus.sponusbe.domain.propose.exception.ProposeErrorCode;
import com.sponus.sponusbe.domain.propose.exception.ProposeException;
import com.sponus.sponusbe.domain.propose.repository.ProposeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

	@Value("${PORT_ONE_KEY}")
	private String restApiKey;
	@Value("${PORT_ONE_SECRET}")
	private String restApiSecret;

	private final ProposeRepository proposeRepository;
	private IamportClient iamportClient;

	@PostConstruct
	public void init() {
		this.iamportClient = new IamportClient(restApiKey, restApiSecret);
	}

	public Payment paymentByImpUid(PaymentRequest request, Organization authOrganization) {
		try {
			log.info("Payment Request : {}", request.toString());
			final Propose propose = proposeRepository.findById(request.proposeId())
				.orElseThrow(() -> new ProposeException(ProposeErrorCode.PROPOSE_NOT_FOUND));
			if (!isProposingOrganization(propose, authOrganization)) {
				throw new ProposeException(ProposeErrorCode.INVALID_PROPOSING_ORGANIZATION);
			}
			if (propose.isPaid()) {
				throw new ProposeException(ProposeErrorCode.PROPOSE_ALREADY_PAID);
			}
			propose.updateToPaid(request.impUid());
			final IamportResponse<Payment> paymentIamportResponse = iamportClient.paymentByImpUid(request.impUid());
			log.info("Payment Response : {}", paymentIamportResponse.getResponse().toString());
			return paymentIamportResponse.getResponse();
		} catch (IamportResponseException | IOException e) {
			log.error("Payment Error : {}", e.getMessage());
			throw new ProposeException(ProposeErrorCode.PAYMENT_ERROR);
		}
	}

	public boolean isProposingOrganization(Propose propose, Organization organization) {
		return propose.getProposingOrganization().getId().equals(organization.getId());
	}
}
