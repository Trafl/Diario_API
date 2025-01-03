package com.api.diario.domain.repository;

import com.api.diario.domain.model.turma.HistoricoTurma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoTurmaRepository extends JpaRepository<HistoricoTurma, Long> {
}
