

delete from authorities;
delete from users;

INSERT INTO users (id,username,password, enabled) VALUES 
 (1,'Administrator','admin', true),
 (2,'Eugen','eugen', true);

INSERT INTO authorities (id, username, authority) VALUES 
 (1,'Administrator', 'ROLE_ADMIN'),
 (2,'Administrator', 'ROLE_USER'),
 (3,'Eugen', 'ROLE_USER');
