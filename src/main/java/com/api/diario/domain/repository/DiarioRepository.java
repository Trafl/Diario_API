package com.api.diario.domain.repository;

import com.api.diario.domain.model.diario.Diario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiarioRepository extends JpaRepository<Diario, Long> {
}
