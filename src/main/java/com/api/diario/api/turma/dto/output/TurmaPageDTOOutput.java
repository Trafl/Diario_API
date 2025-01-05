package com.api.diario.api.turma.dto.output;

import com.api.diario.domain.model.turma.Turno;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TurmaPageDTOOutput {

    private Long id;

    private String numero;

    private Turno turno;

    private Integer anoLetivo;
}
