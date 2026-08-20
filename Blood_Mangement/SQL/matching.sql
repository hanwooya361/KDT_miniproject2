CREATE TABLE matching (
    matching_detail_id INT PRIMARY KEY,
    member_id INT NOT NULL,
    blood_pack_id INT NOT NULL,

    CONSTRAINT fk_matching_shipment
        FOREIGN KEY (member_id)
        REFERENCES shipment(member_id),

    CONSTRAINT fk_matching_blood_pack
        FOREIGN KEY (blood_pack_id)
        REFERENCES blood_pack(blood_pack_id)
);

INSERT INTO matching
(matching_detail_id, member_id, blood_pack_id)
VALUES

(50001, 1, 20001),

(50002, 3, 20003),

(50003, 4, 20007),

(50004, 6, 20002),

(50005, 7, 20005),

(50006, 9, 20004),

(50007, 10, 20008),

(50008, 1, 20006),

(50009, 3, 20010),

(50010, 4, 20009);