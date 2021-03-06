-- liquibase formatted sql

INSERT INTO device_settings(TIME_INTERVAL, NAME)
VALUES (60000, 'Tudor'),
       (60000, 'Cristina'),
       (60000, 'Andrei'),
       (60000, 'Mihai'),
       (60000, 'George');

select @owner_username := 'andrei.test';

SELECT @current_id := ID
FROM device_settings
WHERE name = 'Tudor';
INSERT INTO devices(DEVICE_CODE, DELETED, OWNER_USERNAME, DEVICE_SETTINGS_ID, REGISTERED_ON, REFRESH_TOKEN, TOKEN_API)
VALUES ('MOTR_81160966_0001', 0, @owner_username, @current_id, 1590420635000, 'MOCK_REFRESH_TOKEN', 'MOCK_TOKEN_API');

SELECT @current_id := ID
FROM device_settings
WHERE name = 'Cristina';
INSERT INTO devices(DEVICE_CODE, DELETED, OWNER_USERNAME, DEVICE_SETTINGS_ID, REGISTERED_ON, REFRESH_TOKEN, TOKEN_API)
VALUES ('MOTR_16205921_0002', 0, @owner_username, @current_id, 1590996935000, 'MOCK_REFRESH_TOKEN', 'MOCK_TOKEN_API');

SELECT @current_id := ID
FROM device_settings
WHERE name = 'Andrei';
INSERT INTO devices(DEVICE_CODE, DELETED, OWNER_USERNAME, DEVICE_SETTINGS_ID, REGISTERED_ON, REFRESH_TOKEN, TOKEN_API)
VALUES ('MOTR_65574015_0003', 0, @owner_username, @current_id, 1591185392000, 'MOCK_REFRESH_TOKEN', 'MOCK_TOKEN_API');

SELECT @current_id := ID
FROM device_settings
WHERE name = 'Mihai';
INSERT INTO devices(DEVICE_CODE, DELETED, OWNER_USERNAME, DEVICE_SETTINGS_ID, REGISTERED_ON, REFRESH_TOKEN, TOKEN_API)
VALUES ('MOTR_74342228_0004', 0, @owner_username, @current_id, 1591203122000, 'MOCK_REFRESH_TOKEN', 'MOCK_TOKEN_API');

SELECT @current_id := ID
FROM device_settings
WHERE name = 'George';
INSERT INTO devices(DEVICE_CODE, DELETED, OWNER_USERNAME, DEVICE_SETTINGS_ID, REGISTERED_ON, REFRESH_TOKEN, TOKEN_API)
VALUES ('MOTR_29364991_0005', 0, @owner_username, @current_id, 1591265159000, 'MOCK_REFRESH_TOKEN', 'MOCK_TOKEN_API');

UPDATE constants SET constant_value = 6 where constant_name='UNREGISTERED_DEVICE_INDEX';