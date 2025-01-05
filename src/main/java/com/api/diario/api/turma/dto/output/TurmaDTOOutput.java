package com.api.diario.api.turma.dto.output;

import com.api.diario.api.aluno.dto.output.AlunoPageDTOResumido;
import com.api.diario.domain.model.diario.Diario;
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

    private Set<AlunoPageDTOResumido> alunos;
    // Modificar DIARIO PARA DTO
    private Set<Diario> diarios;
}
