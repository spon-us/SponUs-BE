CREATE TABLE organization
(
    organization_id            BIGSERIAL    NOT NULL,
    created_at                 TIMESTAMP(6),
    updated_at                 TIMESTAMP(6),
    organization_description   VARCHAR(255),
    organization_email         VARCHAR(255) NOT NULL,
    organization_image_url     VARCHAR(255),
    organization_location      VARCHAR(255),
    manager_available_day      VARCHAR(255),
    manager_available_hour     VARCHAR(255),
    manager_contact_preference VARCHAR(255),
    manager_email              VARCHAR(255),
    manager_name               VARCHAR(255),
    manager_phone              VARCHAR(255),
    manager_position           VARCHAR(255),
    organization_name          VARCHAR(255) NOT NULL,
    notifications_enabled      BOOLEAN DEFAULT FALSE,
    organization_status        VARCHAR(255) NOT NULL CHECK (organization_status IN ('ACTIVE', 'INACTIVE')),
    organization_type          VARCHAR(255) NOT NULL CHECK (organization_type IN ('STUDENT', 'COMPANY')),
    organization_password      VARCHAR(255) NOT NULL,
    suborganization_type       VARCHAR(255) CHECK (suborganization_type IN ('STUDENT_COUNCIL', 'CLUB')),
    PRIMARY KEY (organization_id)
);

CREATE TABLE organization_link
(
    link_id         BIGSERIAL    NOT NULL,
    link_name       VARCHAR(255) NOT NULL,
    link_url        VARCHAR(255) NOT NULL,
    organization_id BIGINT       NOT NULL,
    PRIMARY KEY (link_id)
);

CREATE TABLE announcement
(
    announcement_id       BIGSERIAL    NOT NULL,
    created_at            TIMESTAMP(6),
    updated_at            TIMESTAMP(6),
    announcement_category VARCHAR(255) NOT NULL CHECK (announcement_category IN
                                                       ('IDEA', 'MARKETING', 'DESIGN', 'MEDIA', 'DEVELOPMENT',
                                                        'OTHER')),
    announcement_content  VARCHAR(255) NOT NULL,
    announcement_status   VARCHAR(255) NOT NULL CHECK (announcement_status IN ('OPENED', 'CLOSED')),
    announcement_title    VARCHAR(255) NOT NULL,
    announcement_type     VARCHAR(255) NOT NULL CHECK (announcement_type IN ('SPONSORSHIP', 'PARTNERSHIP', 'COLLABORATION')),
    view_count            BIGINT       NOT NULL,
    organization_id       BIGINT,
    PRIMARY KEY (announcement_id)
);

CREATE TABLE announcement_image
(
    image_id        BIGSERIAL    NOT NULL,
    image_name      VARCHAR(255) NOT NULL,
    image_url       VARCHAR(255) NOT NULL,
    announcement_id BIGINT       NOT NULL,
    PRIMARY KEY (image_id)
);

CREATE TABLE bookmark
(
    bookmark_id     BIGSERIAL NOT NULL,
    created_at      TIMESTAMP(6),
    updated_at      TIMESTAMP(6),
    save_count      BIGINT    NOT NULL,
    announcement_id BIGINT    NOT NULL,
    organization_id BIGINT,
    PRIMARY KEY (bookmark_id)
);

CREATE TABLE notification
(
    notification_id      BIGSERIAL             NOT NULL,
    created_at           TIMESTAMP(6),
    updated_at           TIMESTAMP(6),
    notification_body    VARCHAR(255)          NOT NULL,
    notification_is_read BOOLEAN DEFAULT FALSE NOT NULL,
    notification_title   VARCHAR(255)          NOT NULL,
    announcement_id      BIGINT,
    organization_id      BIGINT,
    propose_id           BIGINT,
    report_id            BIGINT,
    PRIMARY KEY (notification_id)
);

CREATE TABLE propose
(
    propose_id                BIGSERIAL    NOT NULL,
    created_at                TIMESTAMP(6),
    updated_at                TIMESTAMP(6),
    propose_content           VARCHAR(255) NOT NULL,
    imp_uid                   VARCHAR(255),
    propose_status            VARCHAR(255) NOT NULL CHECK (propose_status IN
                                                           ('PENDING', 'VIEWED', 'ACCEPTED', 'REJECTED', 'SUSPENDED',
                                                            'PAID', 'COMPLETED')),
    propose_title             VARCHAR(255) NOT NULL,
    announcement_id           BIGINT,
    proposed_organization_id  BIGINT,
    proposing_organization_id BIGINT,
    PRIMARY KEY (propose_id)
);

CREATE TABLE propose_attachment
(
    attachment_id BIGSERIAL    NOT NULL,
    file_name     VARCHAR(255) NOT NULL,
    file_url      VARCHAR(255) NOT NULL,
    propose_id    BIGINT       NOT NULL,
    PRIMARY KEY (attachment_id)
);

CREATE TABLE propose_image
(
    image_id   BIGSERIAL    NOT NULL,
    image_name VARCHAR(255) NOT NULL,
    image_url  VARCHAR(255) NOT NULL,
    propose_id BIGINT       NOT NULL,
    PRIMARY KEY (image_id)
);

CREATE TABLE report
(
    report_id       BIGSERIAL    NOT NULL,
    report_content  VARCHAR(255) NOT NULL,
    report_title    VARCHAR(255) NOT NULL,
    propose_id      BIGINT,
    organization_id BIGINT,
    PRIMARY KEY (report_id)
);

CREATE TABLE report_attachment
(
    attachment_id BIGSERIAL    NOT NULL,
    file_name     VARCHAR(255) NOT NULL,
    file_url      VARCHAR(255) NOT NULL,
    report_id     BIGINT       NOT NULL,
    PRIMARY KEY (attachment_id)
);

CREATE TABLE report_image
(
    image_id   BIGSERIAL    NOT NULL,
    image_name VARCHAR(255) NOT NULL,
    image_url  VARCHAR(255) NOT NULL,
    report_id  BIGINT       NOT NULL,
    PRIMARY KEY (image_id)
);

CREATE TABLE tag
(
    tag_id          BIGSERIAL    NOT NULL,
    tag_name        VARCHAR(255) NOT NULL,
    organization_id BIGINT,
    PRIMARY KEY (tag_id)
);
