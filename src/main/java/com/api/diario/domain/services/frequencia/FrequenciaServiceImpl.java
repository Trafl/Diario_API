package com.api.diario.domain.services.frequencia;

import com.api.diario.api.frequencia.dto.input.FrequenciaDTOInput;
import com.api.diario.api.frequencia.dto.input.FrequenciaUpdateDTOInput;
import com.api.diario.api.frequencia.dto.input.ListFrequenciaDTOInput;
import com.api.diario.domain.exception.aluno.AlunoNotFoundException;
import com.api.diario.domain.exception.frequencia.FrequenciaNotFoundException;
import com.api.diario.domain.model.alunos.Aluno;
import com.api.diario.domain.model.frequencias.Frequencia;
import com.api.diario.domain.repository.FrequenciaRepository;
import com.api.diario.domain.services.aluno.AlunoService;
import com.api.diario.domain.services.trimestre.TrimestreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class FrequenciaServiceImpl implements FrequenciaService {

    private final TrimestreService trimestreService;

    private final AlunoService alunoService;

    private final FrequenciaRepository repository;

    private final ModelMapper mapper;

    String timestamp = LocalDateTime.now().toString();
    @Override
    public Page<Frequencia> getFrequencias(Long alunoId, Long turmaId, String anoLetivo, Long trimestreId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Specification<Frequencia> spec = Specification
                .where(FrequenciaSpecification.byAlunoId(alunoId))
                .and(FrequenciaSpecification.byTurmaId(turmaId))
                .and(FrequenciaSpecification.byAnoLetivo(anoLetivo))
                .and(FrequenciaSpecification.byTrimestreId(trimestreId))
                .and(FrequenciaSpecification.byDataBetween(startDate, endDate));

        log.info("[{}] - [FrequenciaServiceImpl] - Pesquisando Frequencias - getFrequencias()", timestamp);
        return repository.findAll(spec, pageable);
    }

    @Override
    public Frequencia getOneFrequencia(Long frequencia_id) {
        log.info("[{}] - [FrequenciaServiceImpl] - Pesquisando Frequencia de id: {} - getOneFrequencia()", timestamp, frequencia_id);

        return repository.findById(frequencia_id).orElseThrow(
                () -> new FrequenciaNotFoundException(frequencia_id)
        );
    }

    @Async
    @Override
    public void makeCall(ListFrequenciaDTOInput listFrequenciaDTOInput) {
        log.info("Iniciando chamada assíncrona para registrar frequências...");
        executeMakeCallTransactionally(listFrequenciaDTOInput);
        log.info("Chamada assíncrona concluída.");
    }

    @Transactional
    private void executeMakeCallTransactionally(ListFrequenciaDTOInput listFrequenciaDTOInput) {

        log.info("Executando registro de chamadas com controle transacional.");
        var trimestre = trimestreService.getOneTrimestre(listFrequenciaDTOInput.getTrimestre_id());

        List<Long> alunosIds = listFrequenciaDTOInput.getChamadas().stream()
                .map(FrequenciaDTOInput::getAluno_id).toList();

        Map<Long, Aluno> alunosMap = alunoService.getAlunosByIds(alunosIds).stream()
                .collect(Collectors.toMap(Aluno::getId, aluno -> aluno));

        List<Frequencia> frequencias = listFrequenciaDTOInput.getChamadas().stream()
                .map(chamada -> {
                    var aluno = alunosMap.get(chamada.getAluno_id());
                    if (aluno == null) {
                        throw new AlunoNotFoundException(chamada.getAluno_id());
                    }

                    Frequencia frequencia = new Frequencia();
                    frequencia.setAluno(aluno);
                    frequencia.setTrimestre(trimestre);
                    frequencia.setChamada(chamada.getChamada());
                    frequencia.setData(listFrequenciaDTOInput.getData());

                    return frequencia;
                }).toList();

        repository.saveAll(frequencias);
        trimestreService.registerAulaRealizada(trimestre, listFrequenciaDTOInput.getData());

        log.info("Frequências registradas com sucesso para o trimestre {}", trimestre.getId());
    }


    @Override
    @Transactional
    public Frequencia updateFrequencia(Long frequenciaId, FrequenciaUpdateDTOInput updateDTOInput) {
        var frequenciaInDb = getOneFrequencia(frequenciaId);
        mapper.map(updateDTOInput, frequenciaInDb);

        return frequenciaInDb;
    }
}
