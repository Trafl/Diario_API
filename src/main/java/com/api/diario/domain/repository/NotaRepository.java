package com.api.diario.domain.repository;

import com.api.diario.domain.model.alunos.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NotaRepository extends JpaRepository<Nota, Long>, JpaSpecificationExecutor<Nota> {
}
