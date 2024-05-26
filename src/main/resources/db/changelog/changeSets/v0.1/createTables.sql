-- liquibase formatted sql

-- changeset yulia:create_table_users
CREATE TABLE users (UserId INT AUTO_INCREMENT NOT NULL, Name VARCHAR(255) NULL, Email VARCHAR(255) NULL, PhoneNumber VARCHAR(255) NULL, PasswordHash VARCHAR(255) NULL, Role ENUM('CLIENT', 'ADMINISTRATOR') NULL, CONSTRAINT PK_USERS PRIMARY KEY (UserId));

-- changeset alex2808:create_table_cart
CREATE TABLE cart (CartId INT AUTO_INCREMENT NOT NULL,
UserId INT NULL, CONSTRAINT PK_CART PRIMARY KEY (CartId), UNIQUE (UserId));

-- changeset alex2808:create_table_cartitems
CREATE TABLE cartitems (CartItemId INT AUTO_INCREMENT NOT NULL, CartId INT NULL,
ProductId INT NULL, Quantity INT NULL, CONSTRAINT PK_CARTITEMS PRIMARY KEY (CartItemId));

-- changeset yulia:create_table_favorites
CREATE TABLE favorites (FavoriteId INT AUTO_INCREMENT NOT NULL, ProductId INT NULL, UserId INT NULL, CONSTRAINT PK_FAVORITES PRIMARY KEY (FavoriteId));

-- changeset yulia:create_table_orders
CREATE TABLE orders (OrderId INT AUTO_INCREMENT NOT NULL, UserId INT NULL, CreatedAt datetime NULL, DeliveryAddress VARCHAR(255) NULL, ContactPhone VARCHAR(255) NULL, DeliveryMethod VARCHAR(255) NULL, Status ENUM('ORDERED', 'PAID', 'CONFIRMED', 'SENT_TO_WAREHOUSE', 'READY_TO_SHIP', 'SHIPPED_OUT') NULL, UpdatedAt datetime NULL, CONSTRAINT PK_ORDERS PRIMARY KEY (OrderId));

-- changeset yulia:create_table_orderitems
CREATE TABLE orderitems (OrderItemId INT AUTO_INCREMENT NOT NULL, OrderId INT NULL, ProductId INT NULL, Quantity INT NULL, PriceAtPurchase DECIMAL NULL, CONSTRAINT PK_ORDERITEMS PRIMARY KEY (OrderItemId));




-- changeset yulia:create_foreign_key_favorites_users
ALTER TABLE favorites ADD CONSTRAINT foreign_key_favorites_users FOREIGN KEY (UserId) REFERENCES users (UserId) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset yulia:create_foreign_key_cart_users
ALTER TABLE cart ADD CONSTRAINT foreign_key_cart_users FOREIGN KEY (UserId) REFERENCES users (UserId) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset alex2808:create_foreign_key_cartitems_cart
ALTER TABLE cartitems ADD CONSTRAINT foreign_key_cartitems_cart FOREIGN KEY (CartId) REFERENCES cart (CartId) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset yulia:create_foreign_key_orders_users
ALTER TABLE orders ADD CONSTRAINT foreign_key_orders_users FOREIGN KEY (UserId) REFERENCES users (UserId) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset yulia:create_foreign_key_orderitems_orders
ALTER TABLE orderitems ADD CONSTRAINT foreign_key_orderitems_orders FOREIGN KEY (OrderId) REFERENCES orders (OrderId) ON UPDATE RESTRICT ON DELETE RESTRICT;




-- changeset yulia:create_index_favorites
CREATE INDEX foreign_key_favorites_users ON favorites(UserId);

-- changeset yulia:create_index_cart
CREATE INDEX foreign_key_cart_users ON cart(UserId);

-- changeset yulia:create_index_cartitems
CREATE INDEX foreign_key_cartitems_cart ON cartitems(CartId);

-- changeset yulia:create_index_orders
CREATE INDEX foreign_key_orders_users ON orders(UserId);

-- changeset yulia:create_index_orderitems
CREATE INDEX foreign_key_orderitems_orders ON orderitems(OrderId);









