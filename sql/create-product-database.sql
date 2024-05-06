CREATE DATABASE product_service;

CREATE TABLE product_service.product
(
    id   INT          NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price DOUBLE         NOT NULL,
    category_id INT      NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

grant all on product_service.* to webshopuser;
