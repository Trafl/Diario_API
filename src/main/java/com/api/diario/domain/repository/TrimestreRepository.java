package com.api.diario.domain.repository;

import com.api.diario.domain.model.diario.Trimestre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrimestreRepository extends JpaRepository<Trimestre, Long> {
}
