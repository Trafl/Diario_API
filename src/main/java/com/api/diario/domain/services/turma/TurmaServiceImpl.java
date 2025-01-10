package com.api.diario.domain.services.turma;

import com.api.diario.api.turma.dto.input.ListOfAlunosDTOInput;
import com.api.diario.api.turma.mapper.TurmaMapper;
import com.api.diario.domain.exception.historicoturma.HistoricoNotFoundException;
import com.api.diario.domain.exception.turma.TurmaNotFoundException;
import com.api.diario.domain.model.turma.HistoricoTurma;
import com.api.diario.domain.model.turma.Turma;
import com.api.diario.domain.model.turma.Turno;
import com.api.diario.domain.repository.TurmaRepository;
import com.api.diario.domain.services.aluno.AlunoService;
import com.api.diario.domain.services.historicoturma.HistoricoTurmaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class TurmaServiceImpl implements TurmaService{

    private final TurmaRepository turmaRepository;

    private final AlunoService alunoService;

    private final HistoricoTurmaService historicoTurmaService;

    private final TurmaMapper mapper;

    String timestamp = LocalDateTime.now().toString();

    @Override
    @Transactional
    public Turma addTurma(Turma turma) {
        log.info("[{}] - [TurmaServiceImpl] - Adicionando Turma de id: {}", timestamp, turma.getId());
        return turmaRepository.save(turma);
    }

    @Override
    public Turma getOneTurma(Long id) {
        log.info("[{}] - [TurmaServiceImpl] - Procurando Turma de id: {}", timestamp, id);
        return turmaRepository.findById(id).orElseThrow(
                () -> new TurmaNotFoundException(id)
        );
    }

    @Override
    public Page<Turma> listTurmas(String numero, Turno turno, Integer anoLetivo, Long alunoId, Long diarioId, Pageable pageable) {
        log.info("[{}] - [TurmaServiceImpl] - Listando todas as turmas", timestamp);

        Specification<Turma> spec = Specification
                .where(TurmaSpecification.hasNumero(numero))
                .and(TurmaSpecification.hasTurno(turno))
                .and(TurmaSpecification.hasAnoLetivo(anoLetivo))
                .and(TurmaSpecification.hasAlunoWithId(alunoId))
                .and(TurmaSpecification.hasDiarioWithId(diarioId));

        return turmaRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional
    public Turma addAlunosToTurma(Long turma_id, ListOfAlunosDTOInput listAlunoId) {

        log.info("[{}] - [TurmaServiceImpl] - Adicionando Alunos para a Turma de id: {}", timestamp, turma_id);
        var novaTurma = getOneTurma(turma_id);

        for (var alunos : listAlunoId.getAlunos()){
            var aluno = alunoService.getOneAluno(alunos.getAluno_id());
            log.info("[{}] - [TurmaServiceImpl] - Adicionando Aluno de id: {} para a Turma de id: {}", timestamp, aluno.getId(), turma_id);

            // Aluno sem histórico atual - adicionando novo
            if(!aluno.getHistoricoTurmas().isEmpty()){
                HistoricoTurma historicoAtual = aluno.getHistoricoTurmas()
                        .stream()
                        .filter(h -> h.getDataFim() == null)
                        .findFirst()
                        .orElseThrow(
                                () -> new HistoricoNotFoundException(aluno.getId()));
                historicoAtual.setDataFim(LocalDate.now());
            }

            // Criar um novo registro no histórico
            HistoricoTurma novoHistorico = new HistoricoTurma();
            novoHistorico.setAluno(aluno);
            novoHistorico.setTurma(novaTurma);
            novoHistorico.setDataInicio(LocalDate.now());

            aluno.setTurma(novaTurma);
            aluno.getHistoricoTurmas().add(novoHistorico);

            historicoTurmaService.addHistorico(novoHistorico);
            alunoService.addAluno(aluno);

        }
        return novaTurma;
    }

    @Override
    @Transactional
    public Turma updateTurma(Long turma_id, Turma turmaSrc){
        log.info("[{}] - [TurmaServiceImpl] - Atualizando as informações da turma de id: {}", timestamp, turma_id);
        var turma = getOneTurma(turma_id);
        mapper.updateTurma(turmaSrc, turma);
        turma = addTurma(turma);
        log.info("[{}] - [TurmaServiceImpl] - Turma de id: {} foi atualizada", timestamp, turma_id);
        return turma;
    }

    @Override
    public List<String> numerosForDropDown(String anoLetivo, Long diarioId) {
        return turmaRepository.getNumeroTurmasByAnoLetivoAndDiarioId(anoLetivo, diarioId);
    }
}
