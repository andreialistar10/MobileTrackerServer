--liquibase formatted sql

--changeset Andrei:1
CREATE TABLE MY_USER_ROLES(
    USER_ROLE_ID    INT(10)     PRIMARY KEY AUTO_INCREMENT,
    NAME            VARCHAR(55) NOT NULL,
    CONSTRAINT uq_my_user_roles_name UNIQUE (NAME)
);

--changeset Andrei:2
CREATE TABLE MY_USERS(
    USERNAME        VARCHAR(25)   NOT NULL PRIMARY KEY,
    PASSWORD        VARCHAR(255)   NOT NULL,
    USER_ROLE_ID    INT(10)        NOT NULL,
    CONSTRAINT fk_my_users_my_user_role_id FOREIGN KEY (USER_ROLE_ID) REFERENCES MY_USER_ROLES (USER_ROLE_ID)
);

--changeset Andrei:3
CREATE TABLE USER_DETAILS(
    USERNAME    VARCHAR (25)   NOT NULL PRIMARY KEY,
    FIRST_NAME  VARCHAR (55)    NOT NULL,
    LAST_NAME   VARCHAR (65)    NOT NULL,
    EMAIL       VARCHAR (255)   NOT NULL,
    FOREIGN KEY (USERNAME) REFERENCES MY_USERS(USERNAME)
);

--changeset Andrei:4
CREATE TABLE NOT_ACTIVATED_ACCOUNTS(
    TOKEN       VARCHAR (260)   NOT NULL PRIMARY KEY,
    USERNAME    VARCHAR (25)    NOT NULL,
    CONSTRAINT  uq_not_activated_accounts_username  UNIQUE (USERNAME)
);

--changeset Andrei:5
INSERT INTO MY_USER_ROLES (NAME) VALUE ('ACTIVATED_ACCOUNT'), ('NOT_ACTIVATED_ACCOUNT');