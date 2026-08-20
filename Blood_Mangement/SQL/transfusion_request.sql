CREATE TABLE transfusion_request (
    request_id INT PRIMARY KEY,
    requester_id INT NOT NULL,
    request_type VARCHAR(20),
    patient_name VARCHAR(30),
    hospital_name VARCHAR(100),
    blood_type VARCHAR(3),
    requested_quantity INT,
    deadline DATE,
    status DEFAULT '대기중',
    created_at DATE,
    CONSTRAINT fk_request_member
        FOREIGN KEY (requester_id)
        REFERENCES member(member_id)
);

INSERT INTO transfusion_request
(request_id, requester_id, request_type, patient_name,
 hospital_name, blood_type, requested_quantity,
 deadline, status, created_at)
VALUES

(30001, 2, '지정헌혈', '김철수', '서울병원',
 'O+', 3, '2026-08-22', '대기중', '2026-08-18'),

(30002, 5, '지정헌혈', '박영희', '부산병원',
 'A+', 2, '2026-08-21', '대기중', '2026-08-19'),

(30003, 3, '혈액요청', '최민호', '서울병원',
 'B+', 5, '2026-08-20', '완료', '2026-08-20'),

(30004, 8, '혈액요청', '정수진', '인천병원',
 'O-', 2, '2026-08-23', '대기중', '2026-08-21'),

(30005, 2, '지정헌혈', '이현우', '대전병원',
 'O+', 4, '2026-08-24', '대기중', '2026-08-22'),

(30006, 5, '혈액요청', '김예은', '서울병원',
 'AB+', 1, '2026-08-22', '대기중', '2026-08-23'),

(30007, 8, '혈액요청', '장도현', '부산병원',
 'O+', 3, '2026-08-25', '대기중', '2026-08-24'),

(30008, 2, '지정헌혈', '윤서준', '서울병원',
 'A+', 2, '2026-08-20', '완료', '2026-08-25'),

(30009, 5, '혈액요청', '한지민', '인천병원',
 'AB+', 2, '2026-08-26', '대기중', '2026-08-26'),

(30010, 3, '혈액요청', '송지훈', '대전병원',
 'B+', 1, '2026-08-27', '대기중', '2026-08-27');