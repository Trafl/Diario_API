package com.api.diario.api.aluno.dto.output;

import com.api.diario.api.turma.dto.output.TurmaIdDTOOutput;
import com.api.diario.domain.model.alunos.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlunoOneDTOOutput {

    private Long id;

    private String nome;

    private String numeroMatricula;

    private Status status;

    private Boolean isPcd;

    private TurmaIdDTOOutput turma;

}
