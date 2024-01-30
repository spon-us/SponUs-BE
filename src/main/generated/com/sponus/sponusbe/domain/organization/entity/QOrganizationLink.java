package com.sponus.sponusbe.domain.organization.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrganizationLink is a Querydsl query type for OrganizationLink
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrganizationLink extends EntityPathBase<OrganizationLink> {

    private static final long serialVersionUID = 492130717L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrganizationLink organizationLink = new QOrganizationLink("organizationLink");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final QOrganization organization;

    public final StringPath url = createString("url");

    public QOrganizationLink(String variable) {
        this(OrganizationLink.class, forVariable(variable), INITS);
    }

    public QOrganizationLink(Path<? extends OrganizationLink> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrganizationLink(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrganizationLink(PathMetadata metadata, PathInits inits) {
        this(OrganizationLink.class, metadata, inits);
    }

    public QOrganizationLink(Class<? extends OrganizationLink> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.organization = inits.isInitialized("organization") ? new QOrganization(forProperty("organization")) : null;
    }

}

