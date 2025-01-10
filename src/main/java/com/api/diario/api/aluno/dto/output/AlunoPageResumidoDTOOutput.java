package com.api.diario.api.aluno.dto.output;

import com.api.diario.api.turma.dto.output.TurmaIdDTOOutput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class AlunoPageResumidoDTOOutput {

    private Long id;

    private String nome;

    private Boolean isPcd;

    private TurmaIdDTOOutput turma;
}
