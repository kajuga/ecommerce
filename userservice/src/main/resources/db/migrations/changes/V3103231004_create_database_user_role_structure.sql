CREATE TABLE userservice_bd.user_role
(
    id BIGINT(20)  NOT NULL AUTO_INCREMENT,
    role_name VARCHAR(55) CHARACTER SET utf8 NOT NULL,
    description VARCHAR(255) CHARACTER SET utf8 DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000;

CREATE TABLE userservice_bd.user
(
    id BIGINT(20) auto_increment primary key,
    first_name varchar(255) null,
    last_name varchar(255) null,
    email varchar(255) null,
    role_id BIGINT(20) DEFAULT NULL,
    password varchar(255) default null,
    KEY FK_user_role_id (role_id),
    CONSTRAINT FK_user_role_id FOREIGN KEY (role_id) REFERENCES user_role (id) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 70000
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;

