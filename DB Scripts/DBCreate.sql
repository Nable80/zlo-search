USE mysql;

DROP DATABASE IF EXISTS `zlo_storage`;
CREATE DATABASE `zlo_storage` DEFAULT CHARACTER SET cp1251; -- COLLATE utf8_bin;

USE `zlo_storage`;

CREATE TABLE messages (num INT UNIQUE PRIMARY KEY,
                        host CHAR(100),
                        topic TINYINT,
                        title CHAR(255),
                        nick CHAR(100),
                        msgDate DATETIME,
                        reg BOOL,
                        body TEXT,
                        status TINYINT);
