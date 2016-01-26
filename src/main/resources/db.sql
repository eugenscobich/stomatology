delete from authorities;
delete from users;

INSERT INTO users (id,username,password, enabled) VALUES 
 (1, 'admin', 'admin', true),
 (2, 'accountant', 'accountant', true),
 (3, 'employee', 'employee', true),
 (4, 'director', 'director', true);

INSERT INTO authorities (id, username, authority) VALUES 
 (1, 'admin', 'ROLE_ADMIN'),
 (2, 'admin', 'ROLE_ACCOUNTANT'),
 (3, 'admin', 'ROLE_EMPLOYEE'),
 (4, 'admin', 'ROLE_DIRECTOR'),
 
 (5, 'director', 'ROLE_ACCOUNTANT'),
 (6, 'director', 'ROLE_EMPLOYEE'),
 (7, 'director', 'ROLE_DIRECTOR'),

 (8, 'accountant', 'ROLE_ACCOUNTANT'),
 (9, 'accountant', 'ROLE_EMPLOYEE'),
 
 (10, 'employee', 'ROLE_EMPLOYEE');