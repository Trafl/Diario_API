package com.api.diario.domain.repository;

import com.api.diario.domain.model.alunos.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
