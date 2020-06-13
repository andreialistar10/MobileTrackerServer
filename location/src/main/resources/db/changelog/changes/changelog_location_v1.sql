-- liquibase formatted sql

-- changeset Andrei:1
CREATE TABLE LOCATION_SETTINGS
(
    ID                        BIGINT AUTO_INCREMENT PRIMARY KEY,
    GEOFENCE_RADIUS           DOUBLE,
    GEOFENCE_CENTER_LATITUDE  DOUBLE,
    GEOFENCE_CENTER_LONGITUDE DOUBLE
);

-- changeset Andrei:2
CREATE TABLE LOCATIONS
(
    ID            BIGINT AUTO_INCREMENT PRIMARY KEY,
    DEVICE_CODE   VARCHAR(255) NOT NULL,
    LATITUDE      DOUBLE       NOT NULL,
    LONGITUDE     DOUBLE       NOT NULL,
    SENT_AT       BIGINT(20)   NOT NULL,
    LOCATION_NAME VARCHAR(255)
);