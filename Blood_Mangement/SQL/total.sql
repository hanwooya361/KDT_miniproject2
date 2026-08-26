drop database if exists BloodMangement;
create database BloodMangement;
use BloodMangement;

CREATE TABLE member (
    member_id INT AUTO_INCREMENT PRIMARY KEY,
    login_id VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(30) NOT NULL, 
    name VARCHAR(30) NOT NULL,
    phone VARCHAR(20) NOT NULL UNIQUE,
    member_type VARCHAR(20) NOT NULL,
    mcreated_at DATE
);

CREATE TABLE donation_history (
    donation_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT NOT NULL,
    donation_date DATE,
    created_at DATE,
    CONSTRAINT fk_donation_member
        FOREIGN KEY (member_id)
        REFERENCES member(member_id)
) AUTO_INCREMENT = 10000;

CREATE TABLE blood_pack (
    blood_pack_id INT AUTO_INCREMENT PRIMARY KEY,
    blood_type VARCHAR(3),
    donation_id INT,
    received_date DATE,
    expiration_date DATE,
    shipment_date DATE DEFAULT NULL,
    status VARCHAR(10) DEFAULT '보관중',
    CONSTRAINT fk_blood_pack_donation
        FOREIGN KEY (donation_id)
        REFERENCES donation_history(donation_id)
) AUTO_INCREMENT = 20000;

CREATE TABLE transfusion_request (
    request_id INT AUTO_INCREMENT PRIMARY KEY,
    requester_id INT NOT NULL,
    request_type VARCHAR(20),
    patient_name VARCHAR(30),
    hospital_name VARCHAR(100),
    blood_type VARCHAR(3),
    requested_quantity INT,
    deadline DATE,
    status VARCHAR(10) DEFAULT '대기중',
    created_at DATE,
    CONSTRAINT fk_request_member
        FOREIGN KEY (requester_id)
        REFERENCES member(member_id)
) AUTO_INCREMENT = 30000;

CREATE TABLE matching (
    matching_detail_id INT AUTO_INCREMENT PRIMARY KEY,
    blood_pack_id INT NOT NULL,
    request_id INT NOT NULL,
    CONSTRAINT fk_matching_blood_pack
        FOREIGN KEY (blood_pack_id)

        REFERENCES blood_pack(blood_pack_id),
    CONSTRAINT fk_matching_request

        FOREIGN KEY (request_id)
        REFERENCES transfusion_request(request_id)
        
) AUTO_INCREMENT = 40000;


INSERT INTO member
(member_id, login_id, password, phone, member_type, name, mcreated_at)
VALUES
(1, 'hong123', 'Yun!4827qa', '010-1234-5678', '헌혈자', '최윤성', '2026-08-16'),

(2, 'kim123', 'Blue#7315K', '010-2222-3333', '수혈자', '구현승', '2026-08-17'),

(3, 'seou01', 'Mango@9042z', '010-3333-4444', '헌혈자', '조현우', '2026-08-17'),

(4, 'park77', 'Tree$6183Lm', '010-4444-5555', '헌혈자', '최정우', '2026-08-18'),

(5, 'lee100', 'Cloud!5279p', '010-5555-6666', '수혈자', '이서연', '2026-08-18'),

(6, 'choi88', 'River#8406X', '010-6666-7777', '헌혈자', '김도윤', '2026-08-18'),

(7, 'minji22', 'Star@3158nb', '010-7777-8888', '헌혈자', '김민지', '2026-08-19'),

(8, 'jpark55', 'Green$6924Q', '010-8888-9999', '수혈자', '정지훈', '2026-08-19'),

(9, 'sora33', 'Moon!7531rk', '010-9999-0000', '헌혈자', '한소라', '2026-08-19'),

(10, 'yoon10', 'Data#4268Vt', '010-1111-2222', '헌혈자', '윤지호', '2026-08-19'),

(11, 'blue11', 'Sky#5821Lm', '010-2456-7812', '헌혈자', '강지우', '2026-08-20'),

(12, 'star12', 'Wave!7394Qp', '010-3167-4925', '수혈자', '박서준', '2026-08-21'),

(13, 'moon13', 'Leaf@4186Rx', '010-5289-1637', '헌혈자', '김하은', '2026-08-21'),

(14, 'river14', 'Rain$6273Kt', '010-6742-9158', '수혈자', '이준혁', '2026-08-22'),

(15, 'cloud15', 'Snow!8352Vz', '010-7915-3246', '헌혈자', '오수빈', '2026-08-23');


INSERT INTO donation_history 
(donation_id, member_id, donation_date) 
VALUES
(10001, 1, '2026-08-18'),

(10002, 2, '2026-08-18'),

(10003, 3, '2026-08-18'),

(10004, 4, '2026-08-18'),

(10005, 5, '2026-08-18'),

(10006, 6, '2026-08-18'),

(10007, 7, '2026-08-19'),

(10008, 8, '2026-08-19'),

(10009, 9, '2026-08-19'),

(10010, 10, '2026-08-19'),

(10011, 11, '2026-08-18'),

(10012, 12, '2026-08-19'),

(10013, 13, '2026-08-19'),

(10014, 14, '2026-08-19'),

(10015, 15, '2026-08-19');




INSERT INTO blood_pack 
(blood_pack_id, blood_type, donation_id, expiration_date, 
 received_date, shipment_date, status) 
 VALUES
(20001, 'O+',  10001, '2026-09-17', '2026-08-18', '2026-08-19', '출고완료'), 

(20002, 'A+',  10002, '2026-09-17', '2026-08-18', '2026-08-19', '출고완료'), 

(20003, 'B+',  10003, '2026-09-17', '2026-08-18', '2026-08-19', '출고완료'), 

(20004, 'A+',  10004, '2026-09-17', '2026-08-18', NULL, '보관중'),

(20005, 'O-',  10005, '2026-09-17', '2026-08-18', NULL, '보관중'),

(20006, 'AB-', 10006, '2026-09-17', '2026-08-18', NULL, '보관중'),

(20007, 'O+',  10007, '2026-09-18', '2026-08-19', '2026-08-20', '출고완료'), 

(20008, 'O+',  10008, '2026-09-18', '2026-08-19', NULL, '보관중'),

(20009, 'A+',  10009, '2026-09-18', '2026-08-19', '2026-08-19', '출고완료'), 

(20010, 'B+',  10010, '2026-09-18', '2026-08-19', NULL, '보관중'),

(20011, 'AB-', 10011, '2026-09-17', '2026-08-18', '2026-08-20', '출고완료'), 

(20012, 'O+',  10012, '2026-09-18', '2026-08-19', '2026-08-20', '출고완료'), 

(20013, 'A+',  10013, '2026-09-18', '2026-08-19', '2026-08-20', '출고완료'), 

(20014, 'AB+', 10014, '2026-09-18', '2026-08-19', '2026-08-19', '출고완료'), 

(20015, 'B+',  10015, '2026-09-18', '2026-08-19', '2026-08-20', '출고완료');

INSERT INTO transfusion_request 
(request_id, requester_id, request_type, patient_name, 
 hospital_name, blood_type, requested_quantity, deadline, 
 status, created_at) 
 VALUES
(30001, 2, '지정헌혈', '구현승', '서울병원', 'O+', 3, '2026-09-04', '완료', '2026-08-18'),

(30002, 5, '지정헌혈', '이서연', '부산병원', 'A+', 2, '2026-09-02', '대기중', '2026-08-19'),

(30003, 3, '혈액요청', '최민호', '서울병원', 'B+', 5, '2026-08-24', '완료', '2026-08-18'),

(30004, 8, '혈액요청', '정수진', '인천병원', 'O-', 2, '2026-09-03', '대기중', '2026-08-21'),

(30005, 2, '지정헌혈', '구현승', '대전병원', 'O+', 4, '2026-08-29', '완료', '2026-08-22'),

(30006, 5, '혈액요청', '김예은', '서울병원', 'AB+', 1, '2026-08-30', '대기중', '2026-08-23'),

(30007, 8, '혈액요청', '장도현', '부산병원', 'O+', 3, '2026-08-31', '대기중', '2026-08-24'),

(30008, 9, '지정헌혈', '박서준', '서울병원', 'A+', 2, '2026-08-31', '완료', '2026-08-18'),

(30009, 5, '혈액요청', '한지민', '인천병원', 'AB+', 2, '2026-09-04', '대기중', '2026-08-26'),

(30010, 3, '혈액요청', '송지훈', '대전병원', 'B+', 1, '2026-09-03', '대기중', '2026-08-27'),

(30011, 2, '혈액요청', '김민지', '대전병원', 'O+', 4, '2026-08-29', '완료', '2026-08-22'),

(30012, 5, '혈액요청', '김덕수', '서울병원', 'AB-', 1, '2026-08-30', '완료', '2026-08-23'),

(30013, 8, '혈액요청', '최규호', '부산병원', 'O+', 3, '2026-08-31', '완료', '2026-08-24'),

(30014, 2, '혈액요청', '김짱구', '서울병원', 'A+', 2, '2026-08-31', '완료', '2026-08-18'),

(30015, 5, '혈액요청', '최지현', '인천병원', 'AB+', 2, '2026-09-04', '완료', '2026-08-26'),

(30016, 3, '혈액요청', '이서연', '대전병원', 'B+', 1, '2026-09-03', '완료', '2026-08-27');

INSERT INTO matching 
(matching_detail_id, blood_pack_id, request_id) 
VALUES
(40001, 20001, 30001),

(40002, 20002, 30003),

(40003, 20003, 30005),

(40004, 20009, 30008),

(40005, 20011, 30011),

(40006, 20012, 30012),

(40007, 20013, 30013),

(40008, 20014, 30014),

(40009, 20015, 30015),

(40010, 20007, 30016);