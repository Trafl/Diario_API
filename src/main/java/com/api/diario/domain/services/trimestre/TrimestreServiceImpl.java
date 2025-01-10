package com.api.diario.domain.services.trimestre;

import com.api.diario.domain.exception.trimestre.TrimestreNotFoundException;
import com.api.diario.domain.model.trimestre.Trimestre;
import com.api.diario.domain.repository.TrimestreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Log4j2
@Service
@RequiredArgsConstructor
public class TrimestreServiceImpl implements TrimestreService{

    private final TrimestreRepository repository;

    String timestamp = LocalDateTime.now().toString();

    @Override
    public Trimestre addTrimestre(Trimestre trimestre) {
        log.info("[{}] - [TrimestreServiceImpl] - Salvando Trimestre", timestamp);
        return repository.save(trimestre);
    }

    @Override
    public Page<Trimestre> getTrimestres(String nome, Long diarioId, String anoLetivo, String turno, String numeroTurma, String instrumentoNome, LocalDate startDate, LocalDate endDate, Long professorId, Pageable pageable) {
        log.info("[{}] - [TrimestreServiceImpl] - Pesquisando todos os Trimestre ", timestamp);
        Specification<Trimestre> spec = TrimestreSpecification.createSpecification(nome,  diarioId,  anoLetivo,  turno,  numeroTurma,  instrumentoNome,  startDate,  endDate,  professorId);

        return repository.findAll(spec, pageable);
    }

    @Override
    public Trimestre getOneTrimestre(Long trimestre_id) {
        log.info("[{}] - [TrimestreServiceImpl] - Pesquisando Trimestre de id: {}", timestamp, trimestre_id);
        return repository.findById(trimestre_id).
                orElseThrow(
                        () -> new TrimestreNotFoundException(trimestre_id));
    }

    @Override
    public void registerAulaRealizada(Trimestre trimestre, LocalDate data) {
        if (trimestre.getAulasAgendadas() > 0) {
            trimestre.getAulasRealizadas().add(data);

            trimestre.setAulasAgendadas(trimestre.getAulasAgendadas() - 1);
            repository.save(trimestre);
        } else {
            throw new IllegalStateException("Não há aulas agendadas restantes para este trimestre.");
        }
    }

}
