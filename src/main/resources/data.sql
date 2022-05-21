INSERT INTO roles (id, name) VALUES (1, 'USER');
INSERT INTO roles (id, name) VALUES (2, 'ADMIN');

INSERT INTO users (id, username, password, name, email) VALUES (1, 'user', '123', 'User', 'user@email.com');
INSERT INTO users (id, username, password, name, email) VALUES (2, 'admin', '123', 'Admin', 'admin@email.com');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);