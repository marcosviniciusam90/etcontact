CREATE TABLE tb_user (
	id BIGINT(20) IDENTITY(1,1) PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	password VARCHAR(150) NOT NULL
);

CREATE TABLE tb_role (
	id BIGINT(20) IDENTITY(1,1) PRIMARY KEY,
	authority VARCHAR(50) NOT NULL
);

CREATE TABLE tb_user_role (
	user_id BIGINT(20) NOT NULL,
	role_id BIGINT(20) NOT NULL,
	PRIMARY KEY (user_id, role_id),
	FOREIGN KEY (user_id) REFERENCES tb_user(id),
	FOREIGN KEY (role_id) REFERENCES tb_role(id)
);

INSERT INTO tb_user (name, email, password) VALUES ('Marcos Mendonça', 'marcos@gmail.com', '$2y$12$9cKAZ2Paf/kc.84dZ2lI9.ZuwMYBo1cclkqD9YRdgdlkjPeXdRAEm');
INSERT INTO tb_user (name, email, password) VALUES ('Administrador', 'admin@gmail.com', '$2y$12$xexc.fL6hghwBJe5VXOG9emu3Bx9TuE.8YzNiz2WjjxFaz2PSOJpS');

INSERT INTO tb_role (authority) VALUES ('ROLE_DELETE');
INSERT INTO tb_role (authority) VALUES ('ROLE_INSERT');
INSERT INTO tb_role (authority) VALUES ('ROLE_SEARCH');

-- marcos
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 3);

-- admin
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 3);


