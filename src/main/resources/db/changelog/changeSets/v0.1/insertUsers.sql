-- liquibase formatted sql

-- changeset yulia:insert_users
insert into users (name, email, phoneNumber, passwordHash, role)
values
    ('Bill Smith','billsmith@example.com', '+4915245783',  'passOne', 'CLIENT'),
    ('Jane Miller','janemiller@example.com', '+4915736548',  'passTwo', 'CLIENT'),
    ('Big Boss','bigboss@example.com', '+491586666',  'adminPass', 'ADMINISTRATOR');