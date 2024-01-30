package com.sponus.sponusbe.domain.propose.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProposeAttachment is a Querydsl query type for ProposeAttachment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProposeAttachment extends EntityPathBase<ProposeAttachment> {

    private static final long serialVersionUID = 1629811974L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProposeAttachment proposeAttachment = new QProposeAttachment("proposeAttachment");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final QPropose propose;

    public final StringPath url = createString("url");

    public QProposeAttachment(String variable) {
        this(ProposeAttachment.class, forVariable(variable), INITS);
    }

    public QProposeAttachment(Path<? extends ProposeAttachment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProposeAttachment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProposeAttachment(PathMetadata metadata, PathInits inits) {
        this(ProposeAttachment.class, metadata, inits);
    }

    public QProposeAttachment(Class<? extends ProposeAttachment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.propose = inits.isInitialized("propose") ? new QPropose(forProperty("propose"), inits.get("propose")) : null;
    }

}

