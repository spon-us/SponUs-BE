package com.sponus.sponusbe.domain.propose.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPropose is a Querydsl query type for Propose
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPropose extends EntityPathBase<Propose> {

    private static final long serialVersionUID = -693041277L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPropose propose = new QPropose("propose");

    public final com.sponus.sponusbe.global.common.QBaseEntity _super = new com.sponus.sponusbe.global.common.QBaseEntity(this);

    public final com.sponus.sponusbe.domain.announcement.entity.QAnnouncement announcement;

    public final com.sponus.sponusbe.domain.organization.entity.QOrganization companyOrganization;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<ProposeAttachment, QProposeAttachment> proposeAttachments = this.<ProposeAttachment, QProposeAttachment>createList("proposeAttachments", ProposeAttachment.class, QProposeAttachment.class, PathInits.DIRECT2);

    public final com.sponus.sponusbe.domain.organization.entity.QOrganization proposedOrganization;

    public final com.sponus.sponusbe.domain.organization.entity.QOrganization proposingOrganization;

    public final com.sponus.sponusbe.domain.report.entity.QReport report;

    public final EnumPath<ProposeStatus> status = createEnum("status", ProposeStatus.class);

    public final com.sponus.sponusbe.domain.organization.entity.QOrganization studentOrganization;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPropose(String variable) {
        this(Propose.class, forVariable(variable), INITS);
    }

    public QPropose(Path<? extends Propose> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPropose(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPropose(PathMetadata metadata, PathInits inits) {
        this(Propose.class, metadata, inits);
    }

    public QPropose(Class<? extends Propose> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.announcement = inits.isInitialized("announcement") ? new com.sponus.sponusbe.domain.announcement.entity.QAnnouncement(forProperty("announcement"), inits.get("announcement")) : null;
        this.companyOrganization = inits.isInitialized("companyOrganization") ? new com.sponus.sponusbe.domain.organization.entity.QOrganization(forProperty("companyOrganization")) : null;
        this.proposedOrganization = inits.isInitialized("proposedOrganization") ? new com.sponus.sponusbe.domain.organization.entity.QOrganization(forProperty("proposedOrganization")) : null;
        this.proposingOrganization = inits.isInitialized("proposingOrganization") ? new com.sponus.sponusbe.domain.organization.entity.QOrganization(forProperty("proposingOrganization")) : null;
        this.report = inits.isInitialized("report") ? new com.sponus.sponusbe.domain.report.entity.QReport(forProperty("report")) : null;
        this.studentOrganization = inits.isInitialized("studentOrganization") ? new com.sponus.sponusbe.domain.organization.entity.QOrganization(forProperty("studentOrganization")) : null;
    }

}

