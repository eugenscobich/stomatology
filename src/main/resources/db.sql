delete from customers;
delete from authorities;
delete from users;

INSERT INTO users (id,username,password, enabled) VALUES 
 (1, 'admin', 'admin', true),
 (2, 'accountant', 'accountant', true),
 (3, 'employee', 'employee', true),
 (4, 'director', 'director', true),
 (5, 'dentist', 'dentist', true);

INSERT INTO authorities (id, username, authority) VALUES 
 (1, 'admin', 'ROLE_ADMIN'),
 (2, 'admin', 'ROLE_ACCOUNTANT'),
 (3, 'admin', 'ROLE_EMPLOYEE'),
 (4, 'admin', 'ROLE_DIRECTOR'),
 (5, 'admin', 'ROLE_DENTIST'),
 
 (6, 'director', 'ROLE_ACCOUNTANT'),
 (7, 'director', 'ROLE_EMPLOYEE'),
 (8, 'director', 'ROLE_DIRECTOR'),
 (9, 'director', 'ROLE_DENTIST'),

 (10, 'accountant', 'ROLE_ACCOUNTANT'),
 (11, 'accountant', 'ROLE_EMPLOYEE'),
 
 (12, 'dentist', 'ROLE_DENTIST'),
 (13, 'dentist', 'ROLE_EMPLOYEE'),
  
 (14, 'employee', 'ROLE_EMPLOYEE');
 
 
 INSERT INTO customers (id, name, surname, birth_year, gender, address, phone, notes, dentist_id) VALUES 
 (1, 'Vadim', 'Vadimovich', 1980, 'MALE', 'Chisinau Buicani', '0123456789', 'notes \n tre la la', 5);
 