package com.api.diario.domain.services.turma;

import com.api.diario.api.turma.dto.input.ListOfAlunosDTOInput;
import com.api.diario.domain.model.turma.Turma;
import com.api.diario.domain.model.turma.Turno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TurmaService {

    Turma addTurma(Turma turma);

    Turma getOneTurma(Long id);

    Page<Turma> listTurmas(String numero, Turno turno, Integer anoLetivo, Long alunoId, Long diarioId, Pageable pageable);

    Turma addAlunosToTurma(Long turma_id, ListOfAlunosDTOInput listAlunoId);

    Turma updateTurma(Long turma_id, Turma turmaSrc);

    List<String> numerosForDropDown(String anoLetivo,Long diarioId);

}
