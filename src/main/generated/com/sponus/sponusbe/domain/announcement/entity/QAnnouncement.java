package com.sponus.sponusbe.domain.announcement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnnouncement is a Querydsl query type for Announcement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnnouncement extends EntityPathBase<Announcement> {

    private static final long serialVersionUID = -1589881557L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnnouncement announcement = new QAnnouncement("announcement");

    public final com.sponus.sponusbe.global.common.QBaseEntity _super = new com.sponus.sponusbe.global.common.QBaseEntity(this);

    public final EnumPath<com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementCategory> category = createEnum("category", com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementCategory.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementStatus> status = createEnum("status", com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementStatus.class);

    public final StringPath title = createString("title");

    public final EnumPath<com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementType> type = createEnum("type", com.sponus.sponusbe.domain.announcement.entity.enums.AnnouncementType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public final com.sponus.sponusbe.domain.organization.entity.QOrganization writer;

    public QAnnouncement(String variable) {
        this(Announcement.class, forVariable(variable), INITS);
    }

    public QAnnouncement(Path<? extends Announcement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnnouncement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnnouncement(PathMetadata metadata, PathInits inits) {
        this(Announcement.class, metadata, inits);
    }

    public QAnnouncement(Class<? extends Announcement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.writer = inits.isInitialized("writer") ? new com.sponus.sponusbe.domain.organization.entity.QOrganization(forProperty("writer")) : null;
    }

}

