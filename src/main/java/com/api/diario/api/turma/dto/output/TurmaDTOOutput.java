package com.api.diario.api.turma.dto.output;

import com.api.diario.api.aluno.dto.output.AlunoPageResumidoDTOOutput;
import com.api.diario.api.diario.dto.output.DiarioIdDTOOuput;
import com.api.diario.domain.model.turma.Turno;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TurmaDTOOutput {

    private Long id;

    private String numero;

    private Turno turno;

    private Integer anoLetivo;

    private Set<AlunoPageResumidoDTOOutput> alunos;

    private Set<DiarioIdDTOOuput> diarios;
}
