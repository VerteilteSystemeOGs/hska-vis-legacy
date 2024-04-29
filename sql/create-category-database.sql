CREATE DATABASE category_service;

CREATE TABLE category_service.category
(
    id   INT          NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

grant all on category_service.* to webshopuser;
