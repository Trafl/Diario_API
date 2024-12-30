CREATE TABLE historico_turmas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    aluno_id BIGINT NOT NULL,
    turma_id BIGINT NOT NULL,
    data_entrada DATE NOT NULL,
    data_saida DATE,
    FOREIGN KEY (aluno_id) REFERENCES aluno(id) ON DELETE CASCADE,
    FOREIGN KEY (turma_id) REFERENCES turma(id) ON DELETE CASCADE
);

CREATE INDEX idx_turma_turno_ano ON turma(turno, ano_letivo);
CREATE INDEX idx_diario_professor_ano ON diario(professor_id, ano_letivo);
CREATE INDEX idx_trimestre_diario ON trimestre(diario_id);
CREATE INDEX idx_aluno_matricula ON aluno(numero_matricula, turma_id);
CREATE INDEX idx_historico_turmas_aluno ON historico_turmas(aluno_id, turma_id);