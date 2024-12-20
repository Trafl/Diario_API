ALTER TABLE usuario
DROP COLUMN roles;

DROP TABLE roles;

CREATE TABLE usuario_roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE,
    role VARCHAR(255) NOT NULL,
    CONSTRAINT fk_usuario_role_usuario FOREIGN KEY (usuario_id)
    REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE professores (
    id BIGINT NOT NULL,
    especialidade VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_professor_usuario_role FOREIGN KEY (id) REFERENCES usuario_roles (id)
        ON DELETE CASCADE
);

CREATE TABLE diretores (
    id BIGINT NOT NULL,
    departamento VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_diretor_usuario_role FOREIGN KEY (id) REFERENCES usuario_roles (id)
        ON DELETE CASCADE
);

CREATE TABLE secretaria (
    id BIGINT NOT NULL,
    departamento VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_secretaria_usuario_role FOREIGN KEY (id) REFERENCES usuario_roles (id)
        ON DELETE CASCADE
);