package com.sponus.sponusbe.domain.report.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReport is a Querydsl query type for Report
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReport extends EntityPathBase<Report> {

    private static final long serialVersionUID = -1664004795L;

    public static final QReport report = new QReport("report");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<ReportAttachment, QReportAttachment> reportAttachments = this.<ReportAttachment, QReportAttachment>createList("reportAttachments", ReportAttachment.class, QReportAttachment.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public QReport(String variable) {
        super(Report.class, forVariable(variable));
    }

    public QReport(Path<? extends Report> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReport(PathMetadata metadata) {
        super(Report.class, metadata);
    }

}

