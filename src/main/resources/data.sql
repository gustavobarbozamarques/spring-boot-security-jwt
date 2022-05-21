INSERT INTO roles (id, name) VALUES (1, 'USER');
INSERT INTO roles (id, name) VALUES (2, 'ADMIN');

--Raw password: 123456
INSERT INTO users (id, username, password, name, email) VALUES (1, 'user', '$2a$10$e5e6mcGeUiYAx5yMZsGJlOwK8Xl615cMWP4R8e2ykx/CbHu1R0rs.', 'User', 'user@email.com');
INSERT INTO users (id, username, password, name, email) VALUES (2, 'admin', '$2a$10$e5e6mcGeUiYAx5yMZsGJlOwK8Xl615cMWP4R8e2ykx/CbHu1R0rs.', 'Admin', 'admin@email.com');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);