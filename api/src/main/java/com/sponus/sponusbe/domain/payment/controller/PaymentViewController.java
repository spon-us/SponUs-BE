package com.sponus.sponusbe.domain.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentViewController {
	@GetMapping("/payment")
	public String payment() {
		return "payment";
	}
}
