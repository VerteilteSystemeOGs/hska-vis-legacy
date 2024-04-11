CREATE DATABASE user_service;

CREATE TABLE user_service.role (
	id INT NOT NULL AUTO_INCREMENT,
	role_level INT,
	role_type VARCHAR(255),
	PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE user_service.customer (
	id INT NOT NULL AUTO_INCREMENT,
	firstname VARCHAR(255) NOT NULL,
	lastname VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	username VARCHAR(255) NOT NULL,
	role_id INT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE UNIQUE INDEX UK_mufchskagt7e1w4ksmt9lum5l ON user_service.customer (username ASC);

CREATE INDEX FK74aoh99stptslhotgf41fitt0 ON user_service.customer (role_id ASC);

grant all on user_service.* to webshopuser;
