--liquibase formatted sql

--changeset Andrei:1
CREATE TABLE MY_USERS(
    USERNAME VARCHAR(255) NOT NULL PRIMARY KEY,
    PASSWORD VARCHAR(255) NOT NULL
);
--changeset Andrei:2
CREATE TABLE USER_DETAILS(
    USERNAME VARCHAR (255) NOT NULL PRIMARY KEY,
    FIRST_NAME VARCHAR (45) NOT NULL,
    LAST_NAME VARCHAR (45) NOT NULL,
    EMAIL VARCHAR (255) NOT NULL,
    FOREIGN KEY (USERNAME) REFERENCES MY_USERS(USERNAME)
);