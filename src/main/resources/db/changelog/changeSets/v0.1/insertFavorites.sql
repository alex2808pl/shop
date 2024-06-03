-- liquibase formatted sql

-- changeset yulia:insert_favorites
insert into favorites (productId, userId)
values
    (1, 3),
    (1, 1),
    (3, 1),
    (2, 2);