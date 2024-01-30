package com.sponus.sponusbe.domain.announcement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnnouncementImage is a Querydsl query type for AnnouncementImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnnouncementImage extends EntityPathBase<AnnouncementImage> {

    private static final long serialVersionUID = 499705584L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnnouncementImage announcementImage = new QAnnouncementImage("announcementImage");

    public final QAnnouncement announcement;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath url = createString("url");

    public QAnnouncementImage(String variable) {
        this(AnnouncementImage.class, forVariable(variable), INITS);
    }

    public QAnnouncementImage(Path<? extends AnnouncementImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnnouncementImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnnouncementImage(PathMetadata metadata, PathInits inits) {
        this(AnnouncementImage.class, metadata, inits);
    }

    public QAnnouncementImage(Class<? extends AnnouncementImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.announcement = inits.isInitialized("announcement") ? new QAnnouncement(forProperty("announcement"), inits.get("announcement")) : null;
    }

}

