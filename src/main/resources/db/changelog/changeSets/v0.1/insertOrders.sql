-- liquibase formatted sql

-- changeset yulia:insert_orders
insert into Orders (userId, createdAt, deliveryAddress, contactPhone, deliveryMethod, status, updatedAt)
values
    (1, '2024-05-26', 'Cherrystr, 19','+4915245783','Standard','ORDERED','2024-05-26'),
    (2, '2024-05-26', 'Oakstr, 36','+4915634857','Standard','PAID','2024-05-26'),
    (3, '2024-05-26', 'Maplestr, 10','+4915369872','Standard','READY_TO_SHIP','2024-05-26');