CREATE TABLE blood_pack (
    blood_pack_id INT AUTO_INCREMENT PRIMARY KEY,
    blood_type VARCHAR(3),
    donation_id INT NOT NULL,
    received_date DATE,
    expiration_date DATE,
    shipment_date DATE DEFAULT NULL,
    status VARCHAR(10) DEFAULT '보관중',
    CONSTRAINT fk_blood_pack_donation
        FOREIGN KEY (donation_id)
        REFERENCES donation_history(donation_id)
);

INSERT INTO bloodpack
(blood_pack_id, blood_type, donation_id, expiration_date,
 received_date, shipment_date, status)
VALUES

(20001, 'O+', 10001, '2026-09-17',
 '2026-08-18', '2026-08-19', '보관중'),

(20002, 'A+', 10002, '2026-09-17',
 '2026-08-18', '2026-08-19', '보관중'),

(20003, 'B+', 10003, '2026-09-17',
 '2026-08-18', '2026-08-19', '출고완료'),

(20004, 'A+', 10004, '2026-09-17',
 '2026-08-18', '2026-08-19', '보관중'),

(20005, 'O-', 10005, '2026-09-17',
 '2026-08-18', '2026-08-19', '출고완료'),

(20006, 'AB-', 10006, '2026-09-17',
 '2026-08-18', '2026-08-19', '보관중'),

(20007, 'O+', 10007, '2026-09-18',
 '2026-08-19', '2026-08-19', '보관중'),

(20008, 'O+', 10008, '2026-09-18',
 '2026-08-19', '2026-08-19', '보관중'),

(20009, 'A+', 10009, '2026-09-18',
 '2026-08-19', '2026-08-19', '출고완료'),

(20010, 'B+', 10010, '2026-09-18',
 '2026-08-19', '2026-08-19', '보관중');