-- INSERT IGNORE INTO vets VALUES (1, 'James', 'Carter');
-- INSERT IGNORE INTO vets VALUES (2, 'Helen', 'Leary');
-- INSERT IGNORE INTO vets VALUES (3, 'Linda', 'Douglas');
-- INSERT IGNORE INTO vets VALUES (4, 'Rafael', 'Ortega');
-- INSERT IGNORE INTO vets VALUES (5, 'Henry', 'Stevens');
-- INSERT IGNORE INTO vets VALUES (6, 'Sharon', 'Jenkins');


INSERT IGNORE INTO user VALUES (1, 'password', 'daskinne', 'Dr. Dave');
INSERT IGNORE INTO user VALUES (2, 'password', 'pat', 'Patient Pat');
INSERT IGNORE INTO user VALUES (3, 'password', 'steve', 'Staffer Steve');
INSERT IGNORE INTO user VALUES (4, 'password', 'admin', 'Adam the Admin');
INSERT IGNORE INTO user VALUES (5, 'password', 'dan', 'Dr. Dan');

INSERT IGNORE INTO doctor VALUES (1, 9999);
INSERT IGNORE INTO doctor VALUES (5, 10000);

-- Health status => ENUM
-- 0 => Dead
-- 1 => Sick
-- 3 => Healthy
INSERT INTO patient (user_id, version_number, phone_number, health_card, sin, address, current_health, doctor_account) values (2,0,'4152002000','123456789012','123456789','Hospital Ave.',0,1);

-- Assign patient pat to Doctor Dan
INSERT IGNORE INTO pati_doct VALUES (5,2,0);


-- Create 3 Prescriptions
-- Mapped in software to dosages etc.
-- TODO: convert this to something more logical (like a table of details)
INSERT IGNORE INTO legal_prescription VALUES (1);
INSERT IGNORE INTO legal_prescription VALUES (2);
INSERT IGNORE INTO legal_prescription VALUES (3);

-- Assign a legal perscription to Dr. Dave
INSERT IGNORE INTO doct_lega VALUES (1,1);

-- Assign Staffer Steve to Dr. Dave
INSERT IGNORE INTO user_doct VALUES (3,1);

