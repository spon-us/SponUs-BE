INSERT INTO organization (organization_name,
                          organization_email,
                          organization_password,
                          organization_location,
                          organization_type,
                          suborganization_type,
                          manager_name,
                          manager_position,
                          manager_email,
                          manager_phone,
                          manager_available_day,
                          manager_available_hour,
                          manager_contact_preference,
                          organization_status,
                          organization_image_url)
VALUES ('팀 스포너스',
        'sponus@gmail.com',
        '$2a$10$tzrzG/BSFrrye7Kbm4qPYuP6jaQcj5TX5ER1.X/THqkudiSjtEmWW',
        '서울특별시 강남구 테헤란로 427',
        'STUDENT',
        'STUDENT_COUNCIL',
        '이가은',
        'Project Manager',
        'test@gmail.com',
        '01012345678',
        '월-금',
        '09:00-18:00',
        'EMAIL',
        'ACTIVE',
        'https://sponus.s3.ap-northeast-2.amazonaws.com/images/sponus.png');

INSERT INTO announcement (announcement_title,
                          announcement_type,
                          announcement_category,
                          announcement_content,
                          announcement_status,
                          view_count,
                          organization_id,
                          created_at,
                          updated_at)
VALUES ('무신사 스폰서십',
        'SPONSORSHIP',
        'MARKETING',
        '무신사 스폰서십을 진행할 대학교 학생회를 모집합니다.',
        'OPENED',
        0,
        1,
        '2024-02-12 15:29:19.000000',
        '2024-02-12 15:29:19.000000');

INSERT INTO tag (organization_id, tag_name)
VALUES (1, '#무신사'),
       (1, '#스폰서쉽');



INSERT INTO announcement_image (image_name,
                                image_url,
                                announcement_id)
VALUES ('무신사 스폰서십',
        'https://sponus.s3.ap-northeast-2.amazonaws.com/images/sponus.png',
        1);
