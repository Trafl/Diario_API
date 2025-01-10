package com.api.diario.domain.repository;

import com.api.diario.domain.model.instrumento.Instrumento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentoRepository extends JpaRepository<Instrumento, Long> {
}
