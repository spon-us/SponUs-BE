package com.sponus.coredomain.domain.organization.repository;

import static com.sponus.coredomain.domain.organization.QOrganization.*;
import static org.springframework.util.StringUtils.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sponus.coredomain.domain.organization.Organization;
import com.sponus.coredomain.domain.organization.enums.ProfileStatus;
import com.sponus.coredomain.domain.organization.repository.conditions.OrganizationSearchCondition;

import jakarta.persistence.EntityManager;

public class OrganizationRepositoryCustomImpl implements OrganizationRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public OrganizationRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<Organization> searchOrganizationV2(OrganizationSearchCondition condition, Pageable pageable) {

		List<Organization> content = queryFactory
			.selectFrom(organization)
			.where(
				keywordContains(condition.keyword()),
				organizationIdNotEq(condition.organizationId()),
				isActive()
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		long count = queryFactory
			.selectFrom(organization)
			.where(
				keywordContains(condition.keyword()),
				organizationIdNotEq(condition.organizationId()),
				isActive()
			)
			.fetch()
			.size();

		return PageableExecutionUtils.getPage(content, pageable, () -> count);
	}

	private BooleanExpression keywordContains(String keyword) {
		return hasText(keyword) ? organization.name.containsIgnoreCase(keyword) : null;
	}

	private BooleanExpression organizationIdNotEq(Long organizationId) {
		return organizationId != null ? organization.id.ne(organizationId) : null;
	}

	private BooleanExpression isActive() {
		return organization.profileStatus.eq(ProfileStatus.ACTIVE);
	}
}
