-- liquibase formatted sql

-- changeset yulia:insert_orderitems
insert into OrderItems (orderId, productId, quantity, priceAtPurchase)
values
    (1, 1, 400, 200.00),
    (2, 2, 800, 400.00),
    (3, 3, 600, 500.00);