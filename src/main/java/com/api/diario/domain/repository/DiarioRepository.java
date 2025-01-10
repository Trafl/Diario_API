package com.api.diario.domain.repository;

import com.api.diario.domain.model.diario.Diario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiarioRepository extends JpaRepository<Diario, Long>, JpaSpecificationExecutor<Diario> {

    @Query("SELECT DISTINCT d.anoLetivo FROM Diario d WHERE d.professor.id = :professorId ORDER BY d.anoLetivo DESC")
    List<String> getAnosLetivos(@Param("professorId") Long professorId);

}
