-- liquibase formatted sql

-- changeset alex2808:insert_cartitems
insert into cartitems (CartId,Quantity,ProductId)
values
    (1,5,1),
    (2,5,1),
    (2,8,2),
    (3,10,3),
    (1,22,2);