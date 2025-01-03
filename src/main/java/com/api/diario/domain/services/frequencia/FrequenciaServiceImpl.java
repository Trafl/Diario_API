package com.api.diario.domain.services.frequencia;

import com.api.diario.domain.model.alunos.Frequencia;
import com.api.diario.domain.repository.FrequenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FrequenciaServiceImpl implements FrequenciaService {

    private final FrequenciaRepository repository;
    @Override
    public Page<Frequencia> getFrequencias(Long alunoId, Long turmaId, String anoLetivo, Long trimestreId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Specification<Frequencia> spec = Specification
                .where(FrequenciaSpecification.byAlunoId(alunoId))
                .and(FrequenciaSpecification.byTurmaId(turmaId))
                .and(FrequenciaSpecification.byAnoLetivo(anoLetivo))
                .and(FrequenciaSpecification.byTrimestreId(trimestreId))
                .and(FrequenciaSpecification.byDataBetween(startDate, endDate));

        return repository.findAll(spec, pageable);
    }
}
