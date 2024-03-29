package com.sponus.coreinfrasecurity.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.repository.OrganizationRepository;
import com.sponus.coreinfrasecurity.jwt.exception.SecurityCustomException;
import com.sponus.coreinfrasecurity.jwt.exception.SecurityErrorCode;
import com.sponus.coreinfrasecurity.user.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class AuthOrganizationArgumentResolver implements HandlerMethodArgumentResolver {

	private final OrganizationRepository organizationRepository;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasParameterAnnotation = parameter.hasParameterAnnotation(AuthOrganization.class);
		boolean isOrganizationParameterType = parameter.getParameterType().isAssignableFrom(Organization.class);
		return hasParameterAnnotation && isOrganizationParameterType;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		Object userDetails = SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();

		if (userDetails instanceof String) {
			log.error("userDetails is String");
			throw new SecurityCustomException(SecurityErrorCode.TOKEN_NOT_FOUND);
		}

		return organizationRepository.findById(((CustomUserDetails)userDetails).getId())
			.orElseThrow(() -> new SecurityCustomException(SecurityErrorCode.TOKEN_ORGANIZATION_NOT_FOND));
	}
}
