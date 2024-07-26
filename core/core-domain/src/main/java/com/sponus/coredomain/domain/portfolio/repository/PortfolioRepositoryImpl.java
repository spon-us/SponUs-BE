package com.sponus.coredomain.domain.portfolio.repository;

import static com.sponus.coredomain.domain.portfolio.QPortfolio.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sponus.coredomain.domain.portfolio.Portfolio;
import com.sponus.coredomain.domain.portfolio.repository.conditions.PortfolioSearchParam;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PortfolioRepositoryImpl implements PortfolioCustomRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Portfolio> findAllByConditions(PortfolioSearchParam portfolioSearchParam,
		Pageable pageable) {
		JPAQuery<Long> countQuery = queryFactory
			.select(portfolio.count())
			.from(portfolio);

		List<Portfolio> portfolios = queryFactory
			.select(portfolio)
			.from(portfolio)
			.where(
				isClubId(portfolioSearchParam.clubId()),
				isPortfolioId(portfolioSearchParam.portfolioId())
			)
			.orderBy(portfolio.id.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return PageableExecutionUtils.getPage(portfolios, pageable, countQuery::fetchOne);
	}

	private BooleanExpression isClubId(Long clubId) {
		return clubId != null ? portfolio.club.id.eq(clubId) : null;
	}

	private BooleanExpression isPortfolioId(Long portfolioId) {
		return portfolioId != null ? portfolio.club.id.eq(portfolioId) : null;
	}
}
