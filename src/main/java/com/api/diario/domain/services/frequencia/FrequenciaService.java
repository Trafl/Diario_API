package com.api.diario.domain.services.frequencia;

import com.api.diario.domain.model.alunos.Frequencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface FrequenciaService {

    Page<Frequencia> getFrequencias(Long alunoId, Long turmaId, String anoLetivo, Long trimestreId, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
