-- liquibase formatted sql

-- changeset alex2808:insert_cartitems
insert into cartitems (cartId,quantity,productId)
values
    (1,5,1),
    (2,8,2),
    (3,10,3),
    (1,22,2);