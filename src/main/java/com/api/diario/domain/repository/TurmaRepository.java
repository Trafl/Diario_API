package com.api.diario.domain.repository;

import com.api.diario.domain.model.turma.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Long>, JpaSpecificationExecutor<Turma> {

    @Query("SELECT DISTINCT t.numero FROM Turma t JOIN t.diarios d WHERE d.id = :diarioId AND t.anoLetivo = :anoLetivo")
    List<String> getNumeroTurmasByAnoLetivoAndDiarioId(@Param("anoLetivo") String anoLetivo, @Param("diarioId") Long diarioId);
}
