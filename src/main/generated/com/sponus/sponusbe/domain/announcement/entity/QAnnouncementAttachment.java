package com.sponus.sponusbe.domain.announcement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnnouncementAttachment is a Querydsl query type for AnnouncementAttachment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnnouncementAttachment extends EntityPathBase<AnnouncementAttachment> {

    private static final long serialVersionUID = 969494702L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnnouncementAttachment announcementAttachment = new QAnnouncementAttachment("announcementAttachment");

    public final QAnnouncement announcement;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath url = createString("url");

    public QAnnouncementAttachment(String variable) {
        this(AnnouncementAttachment.class, forVariable(variable), INITS);
    }

    public QAnnouncementAttachment(Path<? extends AnnouncementAttachment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnnouncementAttachment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnnouncementAttachment(PathMetadata metadata, PathInits inits) {
        this(AnnouncementAttachment.class, metadata, inits);
    }

    public QAnnouncementAttachment(Class<? extends AnnouncementAttachment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.announcement = inits.isInitialized("announcement") ? new QAnnouncement(forProperty("announcement"), inits.get("announcement")) : null;
    }

}

