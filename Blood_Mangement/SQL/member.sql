CREATE TABLE member (
    member_id INT AUTO_INCREMENT PRIMARY KEY,
    login_id VARCHAR(30) NOT NULL UNIQUE,
    name VARCHAR(30) NOT NULL,
    phone VARCHAR(20) NOT NULL UNIQUE,
    member_type VARCHAR(20) NOT NULL,
    mcreated_at DATE
);


INSERT INTO member
(member_id, login_id, name, phone, member_type, mcreated_at)
VALUES
(1, 'hong123', '조현우', '010-1234-5678', '헌혈자', '2026-08-16'),

(2, 'kim123', '최윤성', '010-2222-3333', '수혈자', '2026-08-17'),

(3, 'seou01', '구현승', '010-3333-4444', '헌혈자', '2026-08-17'),

(4, 'park77', '최정우', '010-4444-5555', '헌혈자', '2026-08-18'),

(5, 'lee100', '이서연', '010-5555-6666', '수혈자', '2026-08-18'),

(6, 'choi88', '김도윤', '010-6666-7777', '헌혈자', '2026-08-18'),

(7, 'minji22', '김민지', '010-7777-8888', '헌혈자', '2026-08-19'),

(8, 'jpark55', '정지훈', '010-8888-9999', '수혈자', '2026-08-19'),

(9, 'sora33', '한소라', '010-9999-0000', '헌혈자', '2026-08-19'),

(10, 'yoon10', '윤지호', '010-1111-2222', '헌혈자', '2026-08-19');
