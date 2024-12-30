package com.api.diario.domain.repository;

import com.api.diario.domain.model.turma.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
}
