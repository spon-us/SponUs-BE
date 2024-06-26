// package com.sponus.coreinfradb.propose;
//
// import java.util.List;
//
// import org.springframework.stereotype.Repository;
//
// import com.querydsl.core.BooleanBuilder;
// import com.querydsl.jpa.impl.JPAQueryFactory;
// import com.sponus.coredomain.domain.propose.Propose;
// import com.sponus.coredomain.domain.propose.QPropose;
//
// import jakarta.persistence.EntityManager;
// import lombok.RequiredArgsConstructor;
//
// @RequiredArgsConstructor
// @Repository
// public class ProposeCustomRepositoryImpl implements ProposeCustomRepository {
//
// 	private final EntityManager entityManager;
//
// 	@Override
//
// 	public List<Propose> findSentPropose(Long id) {
// 		QPropose p = QPropose.propose;
//
// 		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//
// 		return queryFactory.selectFrom(p)
// 			.where(p.proposingOrganization.id.eq(id))
// 			.leftJoin(p.proposingOrganization).fetchJoin()
// 			.fetch();
// 	}
//
// 	@Override
// 	public List<Propose> findReceivedProposeWithAnnouncementId(Long organizationId, Long announcementId) {
// 		QPropose p = QPropose.propose;
//
// 		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//
// 		BooleanBuilder whereClause = new BooleanBuilder();
// 		whereClause.and(p.proposedOrganization.id.eq(organizationId));
//
// 		return queryFactory.selectFrom(p)
// 			.where(whereClause)
// 			.orderBy(p.createdAt.desc())
// 			.leftJoin(p.proposedOrganization).fetchJoin()
// 			.fetch();
// 	}
// }
