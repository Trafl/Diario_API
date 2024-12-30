package com.api.diario.domain.repository;

import com.api.diario.domain.model.alunos.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<Nota, Long> {
}
