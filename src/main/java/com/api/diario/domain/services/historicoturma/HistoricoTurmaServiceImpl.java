package com.api.diario.domain.services.historicoturma;

import com.api.diario.domain.model.turma.HistoricoTurma;
import com.api.diario.domain.repository.HistoricoTurmaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Log4j2
@Service
@RequiredArgsConstructor
public class HistoricoTurmaServiceImpl implements HistoricoTurmaService {

    private final HistoricoTurmaRepository repository;

    String timestamp = LocalDateTime.now().toString();
    @Override
    public HistoricoTurma addHistorico(HistoricoTurma historicoTurma) {
        log.info("[{}] - [HistoricoTurmaServiceImpl] - Salvando Novo Historico de turma para Aluno de id: {}, e Turma de id: {} ",
                timestamp, historicoTurma.getAluno().getId(), historicoTurma.getTurma().getId());
        return repository.save(historicoTurma);
    }

}
