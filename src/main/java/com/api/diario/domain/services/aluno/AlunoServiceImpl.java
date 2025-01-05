package com.api.diario.domain.services.aluno;

import com.api.diario.api.aluno.mapper.AlunoMapper;
import com.api.diario.domain.exception.aluno.AlunoNotFoundException;
import com.api.diario.domain.exception.aluno.DataExistingException;
import com.api.diario.domain.exception.historicoturma.HistoricoNotFoundException;
import com.api.diario.domain.model.alunos.Aluno;
import com.api.diario.domain.model.alunos.Status;
import com.api.diario.domain.model.turma.HistoricoTurma;
import com.api.diario.domain.repository.AlunoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
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
public class AlunoServiceImpl implements AlunoService{

    private final AlunoRepository repository;

    private final AlunoMapper mapper;

    String timestamp = LocalDateTime.now().toString();

    @Override
    public Aluno addAluno(Aluno aluno) {
        log.info("[{}] - [AlunoServiceImpl] - Adicionando Aluno de nome: {} - addAluno()", timestamp, aluno.getNome());
        try{
            return repository.save(aluno);
        }catch (DataIntegrityViolationException e){
            String errorMessage = extractConstraintName(e);
            throw new DataExistingException(errorMessage);
        }
    }

    @Override
    public Aluno getOneAluno(Long id) {
        log.info("[{}] - [AlunoServiceImpl] - Pesquisando Aluno de id: {} - getOneAluno()", timestamp, id);

        return repository.findById(id).orElseThrow(
                () -> new AlunoNotFoundException(id)
        );
    }

    @Override
    public Page<Aluno> listAlunos(String nome, String numeroMatricula, String status, Boolean isPcd, Long turma_id, Pageable pageable) {

        log.info("[{}] - [AlunoServiceImpl] - Pesquisando alunos - listAlunos()", timestamp);

        var spec = Specification.where(AlunoSpecifications.hasNome(nome))
                .and(AlunoSpecifications.hasNumeroMatricula(numeroMatricula))
                .and(AlunoSpecifications.hasStatus(status))
                .and(AlunoSpecifications.isPCD(isPcd))
                .and(AlunoSpecifications.hasTurmaId(turma_id));

        return repository.findAll(spec, pageable);
    }

    @Override
    @Transactional
    public Aluno updateAluno(Long id, Aluno alunoUpdate) {
        log.info("[{}] - [AlunoServiceImpl] - Aluno de id: {} esta sendo atualizado - updateAluno()", timestamp, id);
        var alunoInDb = getOneAluno(id);
        mapper.updateAluno(alunoUpdate, alunoInDb);
        return repository.save(alunoInDb);
    }

    @Override
    @Transactional
    public void disableAluno(Long id) {
        //Criar função para encerrar a turma no historico do aluno ao desabilitar
        var aluno = getOneAluno(id);

        if (aluno.getStatus() == Status.INATIVO) {
            log.warn("[{}] - [AlunoServiceImpl] - Aluno de id: {} já está inativo - disableAluno()", timestamp, aluno.getId());
            return;
        }
        aluno.setStatus(Status.INATIVO);

        finishHistorico(id, aluno);

        repository.save(aluno);
        log.info("[{}] - [AlunoServiceImpl] - Aluno de id: {} foi desabilitado - disableAluno()", timestamp, aluno.getId());
    }

    @Override
    @Transactional
    public void transferAluno(Long id) {
        //Criar função para encerrar a turma no historico do aluno ao transferir
        var aluno = getOneAluno(id);

        if (aluno.getStatus() == Status.TRANSFERIDO) {
            log.warn("[{}] - [AlunoServiceImpl] - Aluno de id: {} já está transferido - transferAluno()", timestamp, aluno.getId());
            return;
        }
        aluno.setStatus(Status.TRANSFERIDO);
        finishHistorico(id, aluno);

        repository.save(aluno);
        log.info("[{}] - [AlunoServiceImpl] - Aluno de id: {} foi transferido - TransferAluno()", timestamp, aluno.getId());
    }

    @Override
    public List<Aluno> getAlunosByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }

    private String extractConstraintName(DataIntegrityViolationException e) {
        if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException constraintException) {
            String constraintName = constraintException.getConstraintName();
            if (constraintName != null) {
                if (constraintName.contains("matricula")) {
                    return "O número de matrícula já está cadastrado.";
                }
                // Adicione mais verificações conforme necessário para outros campos únicos
            }
        }
        return "Dados já existentes no sistema.";
    }

    private void finishHistorico(Long id, Aluno aluno) {
        HistoricoTurma historicoAtual = aluno.getHistoricoTurmas()
                .stream()
                .filter(h -> h.getDataFim() == null)
                .findFirst()
                .orElseThrow(
                        () -> new HistoricoNotFoundException(id));
        historicoAtual.setDataFim(LocalDate.now());
    }
}
