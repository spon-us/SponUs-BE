package com.sponus.sponusbe.domain.report.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReportAttachment is a Querydsl query type for ReportAttachment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReportAttachment extends EntityPathBase<ReportAttachment> {

    private static final long serialVersionUID = 2026008648L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReportAttachment reportAttachment = new QReportAttachment("reportAttachment");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final QReport report;

    public final StringPath url = createString("url");

    public QReportAttachment(String variable) {
        this(ReportAttachment.class, forVariable(variable), INITS);
    }

    public QReportAttachment(Path<? extends ReportAttachment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReportAttachment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReportAttachment(PathMetadata metadata, PathInits inits) {
        this(ReportAttachment.class, metadata, inits);
    }

    public QReportAttachment(Class<? extends ReportAttachment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.report = inits.isInitialized("report") ? new QReport(forProperty("report")) : null;
    }

}

