package com.api.diario.domain.repository;

import com.api.diario.domain.model.alunos.Frequencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FrequenciaRepository extends JpaRepository<Frequencia, Long>, JpaSpecificationExecutor<Frequencia> {
}
