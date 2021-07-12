CREATE TABLE tb_user (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	cpf VARCHAR(50) NOT NULL,
    birth_Date date NOT NULL,
	email VARCHAR(50) NOT NULL,
	password VARCHAR(150) NOT NULL
);

CREATE TABLE tb_role (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	authority VARCHAR(50) NOT NULL
);

CREATE TABLE tb_user_role (
	user_id BIGINT(20) NOT NULL,
	role_id BIGINT(20) NOT NULL,
	PRIMARY KEY (user_id, role_id),
	FOREIGN KEY (user_id) REFERENCES tb_user(id),
	FOREIGN KEY (role_id) REFERENCES tb_role(id)
);

INSERT INTO tb_user (name, cpf, birth_Date, email, password) VALUES ('Administrador', '111.111.111-11', '1992-12-12', 'admin@gmail.com', '$2y$12$xexc.fL6hghwBJe5VXOG9emu3Bx9TuE.8YzNiz2WjjxFaz2PSOJpS');
INSERT INTO tb_user (name, cpf, birth_Date, email, password) VALUES ('Marcos Vinicius', '073.425.123-12', '1990-10-19', 'marcos@gmail.com', '$2y$12$9cKAZ2Paf/kc.84dZ2lI9.ZuwMYBo1cclkqD9YRdgdlkjPeXdRAEm');

INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_VISITOR');

-- Administrador
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);

-- Marcos
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);


