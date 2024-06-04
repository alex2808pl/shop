-- liquibase formatted sql

-- changeset yulia:create_table_users
CREATE TABLE users (userId INT AUTO_INCREMENT NOT NULL, name VARCHAR(255) NULL, email VARCHAR(255) NULL, phoneNumber VARCHAR(255) NULL, passwordHash VARCHAR(255) NULL, role ENUM('CLIENT', 'ADMINISTRATOR') NULL, CONSTRAINT PK_USERS PRIMARY KEY (userId));

-- changeset alex2808:create_table_cart

CREATE TABLE cart (cartId INT AUTO_INCREMENT NOT NULL,
userId INT NULL, CONSTRAINT PK_CART PRIMARY KEY (cartId), UNIQUE (userId));

-- changeset alex2808:create_table_cartitems
CREATE TABLE cartitems (cartItemId INT AUTO_INCREMENT NOT NULL, cartId INT NULL,
productId INT NULL, quantity INT NULL, CONSTRAINT PK_CARTITEMS PRIMARY KEY (cartItemId));

-- changeset yulia:create_table_favorites
CREATE TABLE favorites (favoriteId INT AUTO_INCREMENT NOT NULL, productId INT NULL, userId INT NULL, CONSTRAINT PK_FAVORITES PRIMARY KEY (favoriteId));

-- changeset yulia:create_table_orders
CREATE TABLE orders (orderId INT AUTO_INCREMENT NOT NULL, userId INT NULL, createdAt datetime NULL, deliveryAddress VARCHAR(255) NULL, contactPhone VARCHAR(255) NULL, deliveryMethod VARCHAR(255) NULL, status ENUM('ORDERED', 'PAID', 'CONFIRMED', 'SENT_TO_WAREHOUSE', 'READY_TO_SHIP', 'SHIPPED_OUT') NULL, updatedAt datetime NULL, CONSTRAINT PK_ORDERS PRIMARY KEY (orderId));

-- changeset yulia:create_table_orderitems
CREATE TABLE orderitems (orderItemId INT AUTO_INCREMENT NOT NULL, orderId INT NULL, productId INT NULL, quantity INT NULL, priceAtPurchase DECIMAL NULL, CONSTRAINT PK_ORDERITEMS PRIMARY KEY (orderItemId));



-- changeset yulia:create_foreign_key_favorites_users
ALTER TABLE favorites ADD CONSTRAINT foreign_key_favorites_users FOREIGN KEY (userId) REFERENCES users (userId) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset yulia:create_foreign_key_cart_users
ALTER TABLE cart ADD CONSTRAINT foreign_key_cart_users FOREIGN KEY (userId) REFERENCES users (userId) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset alex2808:create_foreign_key_cartitems_cart
ALTER TABLE cartitems ADD CONSTRAINT foreign_key_cartitems_cart FOREIGN KEY (cartId) REFERENCES cart (cartId) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset yulia:create_foreign_key_orders_users
ALTER TABLE orders ADD CONSTRAINT foreign_key_orders_users FOREIGN KEY (userId) REFERENCES users (userId) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset yulia:create_foreign_key_orderitems_orders
ALTER TABLE orderitems ADD CONSTRAINT foreign_key_orderitems_orders FOREIGN KEY (orderId) REFERENCES orders (orderId) ON UPDATE RESTRICT ON DELETE RESTRICT;




-- changeset yulia:create_index_favorites
CREATE INDEX foreign_key_favorites_users ON favorites(userId);

-- changeset yulia:create_index_cart
CREATE INDEX foreign_key_cart_users ON cart(userId);

-- changeset yulia:create_index_cartitems
CREATE INDEX foreign_key_cartitems_cart ON cartitems(cartId);

-- changeset yulia:create_index_orders
CREATE INDEX foreign_key_orders_users ON orders(userId);

-- changeset yulia:create_index_orderitems
CREATE INDEX foreign_key_orderitems_orders ON orderitems(orderId);









