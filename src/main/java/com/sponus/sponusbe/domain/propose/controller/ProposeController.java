package com.sponus.sponusbe.domain.propose.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sponus.sponusbe.domain.propose.service.ProposeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProposeController {
	private final ProposeService proposeService;
}
