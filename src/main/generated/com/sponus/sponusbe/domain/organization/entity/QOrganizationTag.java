package com.sponus.sponusbe.domain.organization.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrganizationTag is a Querydsl query type for OrganizationTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrganizationTag extends EntityPathBase<OrganizationTag> {

    private static final long serialVersionUID = 1124261271L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrganizationTag organizationTag = new QOrganizationTag("organizationTag");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QOrganization organization;

    public final QTag tag;

    public QOrganizationTag(String variable) {
        this(OrganizationTag.class, forVariable(variable), INITS);
    }

    public QOrganizationTag(Path<? extends OrganizationTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrganizationTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrganizationTag(PathMetadata metadata, PathInits inits) {
        this(OrganizationTag.class, metadata, inits);
    }

    public QOrganizationTag(Class<? extends OrganizationTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.organization = inits.isInitialized("organization") ? new QOrganization(forProperty("organization")) : null;
        this.tag = inits.isInitialized("tag") ? new QTag(forProperty("tag")) : null;
    }

}

