package com.api.diario.api.aluno.dto.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class AlunoPageDTOResumido {

    private Long id;

    private String nome;

    private Boolean isPcd;

    private Long turma_id;
}
