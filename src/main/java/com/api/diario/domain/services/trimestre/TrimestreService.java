package com.api.diario.domain.services.trimestre;

import com.api.diario.domain.model.alunos.Aluno;
import com.api.diario.domain.model.diario.Trimestre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface TrimestreService {

    Trimestre addTrimestre(Trimestre trimestre);

    Page<Trimestre> getTrimestres(String nome, Long diarioId, String anoLetivo, String turno,
                                  String numeroTurma, String instrumentoNome, LocalDate startDate,
                                  LocalDate endDate, Long professorId, Pageable pageable);

    Trimestre getOneTrimestre(Long trimestre_id);

    void registerAulaRealizada(Trimestre trimestre, LocalDate data);


    // Resumo do Trimestre()
}
