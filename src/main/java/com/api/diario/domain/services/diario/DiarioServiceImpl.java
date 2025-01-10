package com.api.diario.domain.services.diario;

import com.api.diario.domain.exception.diario.DiarioNotFoundException;
import com.api.diario.domain.model.diario.Diario;
import com.api.diario.domain.repository.DiarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class DiarioServiceImpl implements DiarioService {

    private final DiarioRepository repository;

    String timestamp = LocalDateTime.now().toString();

    @Override
    public Page<Diario> getDiarios(Long professorId, String anoLetivo, String materia, Long turmaId, Pageable pageable) {
        log.info("[{}] - [DiarioServiceImpl] - Pesquisando Diarios - getDiarios()", timestamp);
        Specification<Diario> spec = DiarioSpecification.createSpecification(professorId, anoLetivo, materia, turmaId);
        return repository.findAll(spec, pageable);
    }

    public Diario getOneDiario(Long diarioId){
        log.info("[{}] - [DiarioServiceImpl] - Consultando Diario de id: {} - getOneDiario()", timestamp, diarioId);
        return repository.findById(diarioId).orElseThrow(
                () -> new DiarioNotFoundException(diarioId));
    }

    @Override
    @Cacheable("anosLetivos")
    public List<String> getAnosLetivos(Long professorId) {
        log.info("[{}] - [DiarioServiceImpl] - Buscando DropDowns de anos letivos com professor de id: {} - getAnosLetivos()", timestamp, professorId);

        return repository.getAnosLetivos(professorId);
    }
}
