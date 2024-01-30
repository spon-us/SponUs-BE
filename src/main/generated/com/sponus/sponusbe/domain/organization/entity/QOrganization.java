package com.sponus.sponusbe.domain.organization.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrganization is a Querydsl query type for Organization
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrganization extends EntityPathBase<Organization> {

    private static final long serialVersionUID = 177366787L;

    public static final QOrganization organization = new QOrganization("organization");

    public final com.sponus.sponusbe.global.common.QBaseEntity _super = new com.sponus.sponusbe.global.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath location = createString("location");

    public final StringPath managerAvailableDay = createString("managerAvailableDay");

    public final StringPath managerAvailableHour = createString("managerAvailableHour");

    public final StringPath managerContactPreference = createString("managerContactPreference");

    public final StringPath managerEmail = createString("managerEmail");

    public final StringPath managerName = createString("managerName");

    public final StringPath managerPhone = createString("managerPhone");

    public final StringPath managerPosition = createString("managerPosition");

    public final StringPath name = createString("name");

    public final ListPath<OrganizationLink, QOrganizationLink> organizationLinks = this.<OrganizationLink, QOrganizationLink>createList("organizationLinks", OrganizationLink.class, QOrganizationLink.class, PathInits.DIRECT2);

    public final EnumPath<com.sponus.sponusbe.domain.organization.entity.enums.OrganizationStatus> organizationStatus = createEnum("organizationStatus", com.sponus.sponusbe.domain.organization.entity.enums.OrganizationStatus.class);

    public final ListPath<OrganizationTag, QOrganizationTag> organizationTags = this.<OrganizationTag, QOrganizationTag>createList("organizationTags", OrganizationTag.class, QOrganizationTag.class, PathInits.DIRECT2);

    public final EnumPath<com.sponus.sponusbe.domain.organization.entity.enums.OrganizationType> organizationType = createEnum("organizationType", com.sponus.sponusbe.domain.organization.entity.enums.OrganizationType.class);

    public final StringPath password = createString("password");

    public final EnumPath<com.sponus.sponusbe.domain.organization.entity.enums.SuborganizationType> suborganizationType = createEnum("suborganizationType", com.sponus.sponusbe.domain.organization.entity.enums.SuborganizationType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QOrganization(String variable) {
        super(Organization.class, forVariable(variable));
    }

    public QOrganization(Path<? extends Organization> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrganization(PathMetadata metadata) {
        super(Organization.class, metadata);
    }

}

