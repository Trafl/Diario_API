CREATE TABLE turma (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero VARCHAR(50) NOT NULL,
    turno VARCHAR(50) NOT NULL,
    ano_letivo INT NOT NULL
);

CREATE TABLE aluno (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    numero_matricula VARCHAR(50) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    is_pcd TINYINT(1),
    turma_id BIGINT,
    FOREIGN KEY (turma_id) REFERENCES turma(id) ON DELETE SET NULL
);

CREATE TABLE diario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    professor_id BIGINT NOT NULL,
    materia VARCHAR(255) NOT NULL,
    turma_id BIGINT NOT NULL,
    ano_letivo VARCHAR(20) NOT NULL,
    data_criacao DATE NOT NULL,
    FOREIGN KEY (professor_id) REFERENCES usuario_roles(id) ON DELETE CASCADE,
    FOREIGN KEY (turma_id) REFERENCES turma(id) ON DELETE CASCADE
);

CREATE TABLE trimestre (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    diario_id BIGINT NOT NULL,
    aulas_agendadas INT NOT NULL,
    FOREIGN KEY (diario_id) REFERENCES diario(id) ON DELETE CASCADE
);

CREATE TABLE aulas_realizadas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    trimestre_id BIGINT NOT NULL,
    data DATE NOT NULL,
    FOREIGN KEY (trimestre_id) REFERENCES trimestre(id) ON DELETE CASCADE
);

CREATE TABLE instrumento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    valor FLOAT NOT NULL,
    trimestre_id BIGINT NOT NULL,
    FOREIGN KEY (trimestre_id) REFERENCES trimestre(id) ON DELETE CASCADE
);

CREATE TABLE nota (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nota FLOAT,
    nota_recuperacao FLOAT,
    nota_maxima FLOAT,
    instrumento_id BIGINT NOT NULL,
    aluno_id BIGINT NOT NULL,
    FOREIGN KEY (instrumento_id) REFERENCES instrumento(id) ON DELETE CASCADE,
    FOREIGN KEY (aluno_id) REFERENCES aluno(id) ON DELETE CASCADE
);

CREATE TABLE frequencia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    data DATE NOT NULL,
    chamada VARCHAR(10) NOT NULL,
    aluno_id BIGINT NOT NULL,
    trimestre_id BIGINT NOT NULL,
    FOREIGN KEY (aluno_id) REFERENCES aluno(id) ON DELETE CASCADE,
    FOREIGN KEY (trimestre_id) REFERENCES trimestre(id) ON DELETE CASCADE
);