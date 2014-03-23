-- INSERT IGNORE INTO vets VALUES (1, 'James', 'Carter');
-- INSERT IGNORE INTO vets VALUES (2, 'Helen', 'Leary');
-- INSERT IGNORE INTO vets VALUES (3, 'Linda', 'Douglas');
-- INSERT IGNORE INTO vets VALUES (4, 'Rafael', 'Ortega');
-- INSERT IGNORE INTO vets VALUES (5, 'Henry', 'Stevens');
-- INSERT IGNORE INTO vets VALUES (6, 'Sharon', 'Jenkins');


INSERT IGNORE INTO user VALUES ('daskinne', 'password', 'Dr. Dave', false);
INSERT IGNORE INTO user VALUES ('pat', 'password',  'Patient Pat', false);
INSERT IGNORE INTO user VALUES ('steve', 'password', 'Staffer Steve', false);
INSERT IGNORE INTO user VALUES ('admin', 'password', 'Adam the Admin', true);
INSERT IGNORE INTO user VALUES ('dan', 'password','Dr. Dan', false);

INSERT IGNORE INTO doctor VALUES ('daskinne', 9999);
INSERT IGNORE INTO doctor VALUES ('dan', 10000);

-- Health status => ENUM
-- 0 => Dead
-- 1 => Sick
-- 3 => Healthy
INSERT INTO patient (user_id, version_number, phone_number, health_card, sin, address, current_health, doctor_account) values ('pat',0,'4152002000','123456789012','123456789','Hospital Ave.',0,'daskinne');

-- Assign patient pat to Doctor Dan
INSERT IGNORE INTO pati_doct VALUES ('dan','pat',0);


-- Create 3 Prescriptions
-- Mapped in software to dosages etc.
-- TODO: convert this to something more logical (like a table of details)
INSERT IGNORE INTO legal_prescription VALUES (1);
INSERT IGNORE INTO legal_prescription VALUES (2);
INSERT IGNORE INTO legal_prescription VALUES (3);

-- Assign a legal perscription to Dr. Dave
INSERT IGNORE INTO doct_lega VALUES ('daskinne',1);

-- Assign Staffer Steve to Dr. Dave
INSERT IGNORE INTO user_doct VALUES ('steve','daskinne');

