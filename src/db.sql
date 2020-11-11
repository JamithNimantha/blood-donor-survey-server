DROP DATABASE bds;
CREATE DATABASE IF NOT EXISTS bds;
USE bds;
CREATE TABLE user
(
    id            INT  NOT NULL AUTO_INCREMENT,
    full_name     VARCHAR(200) NOT NULL,
    email         VARCHAR(100) NOT NULL,
    mobile_number VARCHAR(20)  NOT NULL,
    type          VARCHAR(10),
    password      VARCHAR(500),
    CONSTRAINT PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

INSERT INTO user
VALUES (1, 'Jamith Nimantha', 'jamith@gmail.com', '0771212123', 'ADMIN', '123456');
