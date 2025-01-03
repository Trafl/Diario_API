package com.api.diario.domain.services.nota;

import com.api.diario.domain.model.alunos.Nota;
import com.api.diario.domain.repository.NotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotaServiceImpl implements NotaService {

    private final NotaRepository repository;

    public Page<Nota>getNotas(Long alunoId, Long turmaId, String anoLetivo, Long trimestreId, Long instrumentoId, Pageable pageable) {
        Specification<Nota> spec = Specification
                .where(NotaSpecification.byAlunoId(alunoId))
                .and(NotaSpecification.byTurmaId(turmaId))
                .and(NotaSpecification.byAnoLetivo(anoLetivo))
                .and(NotaSpecification.byTrimestreId(trimestreId))
                .and(NotaSpecification.byInstrumentoId(instrumentoId));

        return repository.findAll(spec, pageable);
    }
}
