CREATE TABLE donation_history (
    donation_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT NOT NULL,
    donation_date DATE NOT NULL,
    dcreated_at DATE,
    CONSTRAINT fk_donation_member
        FOREIGN KEY (member_id)
        REFERENCES member(member_id)
);

INSERT INTO donation_history
(donation_id, member_id, donation_date)
VALUES
(10001, 1, '2026-08-18'),

(10002, 3, '2026-08-18'),

(10003, 4, '2026-08-18'),

(10004, 6, '2026-08-18'),

(10005, 7, '2026-08-18'),

(10006, 9, '2026-08-18'),

(10007, 10, '2026-08-19'),

(10008, 1, '2026-08-19'),

(10009, 3, '2026-08-19'),

(10010, 4, '2026-08-19');