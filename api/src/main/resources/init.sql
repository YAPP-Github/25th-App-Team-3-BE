-- ProfileBasic 더미 데이터 삽입
INSERT INTO profile (profile_id, nickname, birthdate, height, job, location, smoking_status, religion, sns_activity_level, phone_number,
                     image_url)
VALUES (1, 'JohnDoe', '1990-01-01', 180, 'Engineer', 'Seoul', 'Non-smoker', 'None', 'Medium', '010-1234-5678',
        'https://example.com/johndoe.jpg');

-- ProfileBio 더미 데이터 삽입
UPDATE profile
SET introduction = 'Hello, I am John.',
    goal         = 'Achieve success.',
    interest     = 'Coding'
WHERE profile_id = 1;

-- User 더미 데이터 삽입
INSERT INTO user_common (user_id, oauth_id, name, profile_id, role)
VALUES (1, 'oauth123', 'John Doe', 1, 'USER');

-- ValueItem 더미 데이터 삽입
INSERT INTO value_item (value_item_id, category, question, answers)
VALUES (1, '음주', '연인과 함께 술을 마시는 것을 좋아하나요?', '{"1": "함께 술을 즐기고 싶어요", "2": "같이 술을 즐길 수 없어도 괜찮아요"}'),
       (2, '만남 빈도', '주말에 얼마나 자주 데이트를 하고 싶나요?', '{"1": "주말에는 최대한 같이 있고 싶어요", "2": "하루 정도는 각자 보내고 싶어요"}'),
       (3, '연락 빈도', '연인 사이에 얼마나 자주 연락하는게 좋나요?', '{"1": "바쁘더라도 자주 연락하고 싶어요", "2": "연락은 생각날 때만 종종 해도 괜찮아요"}'),
       (4, '연락 방식', '연락할 때 어떤 방법을 더 좋아하나요?', '{"1": "전화보다는 문자나 카톡이 더 좋아요", "2": "문자나 카톡보다는 전화가 더 좋아요"}');

-- ProfileValue 더미 데이터 삽입 (User가 선택한 ValueItem의 답변)
INSERT INTO profile_value (profile_value_id, profile_id, value_item_id, selected_answer)
VALUES (1, 1, 1, 2),
       (2, 1, 2, 3);

INSERT INTO term (version, title, content, required, start_date, is_active)
VALUES
    ('1.0', '서비스 이용약관', '서비스 이용에 대한 약관 내용입니다.', true, '2024-01-01 00:00:00', true),
    ('1.0', '개인정보 처리방침', '개인정보 보호에 대한 약관 내용입니다.', true, '2024-01-01 00:00:00', true),
    ('1.0', '위치 정보 이용약관', '위치 정보 활용에 대한 약관 내용입니다.', false, '2024-01-01 00:00:00', true),
    ('1.1', '서비스 이용약관', '서비스 이용약관이 업데이트되었습니다.', true, '2024-06-01 00:00:00', true),
    ('1.1', '광고 수신 동의', '광고 수신 동의 약관입니다.', false, '2024-06-01 00:00:00', false)