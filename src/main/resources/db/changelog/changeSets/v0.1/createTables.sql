-- liquibase formatted sql

-- changeset alex2808:create_table_cart
CREATE TABLE cart (CartId INT AUTO_INCREMENT NOT NULL,
UserId INT NULL, CONSTRAINT PK_CART PRIMARY KEY (CartId), UNIQUE (UserId));

-- changeset alex2808:create_table_cartitems
CREATE TABLE cartitems (CartItemId INT AUTO_INCREMENT NOT NULL, CartId INT NULL,
ProductId INT NULL, Quantity INT NULL, CONSTRAINT PK_CARTITEMS PRIMARY KEY (CartItemId));

-- changeset alex2808:create_foreign_key_cartitems_cart
ALTER TABLE cartitems ADD CONSTRAINT foreign_key_cartitems_cart FOREIGN KEY (CartId) REFERENCES cart (CartId) ON UPDATE RESTRICT ON DELETE RESTRICT;



