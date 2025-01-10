package com.api.diario.api.diario.dto.output;

import com.api.diario.api.turma.dto.output.TurmaIdDTOOutput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiarioPageDTOOutput {

    private Long id;

    private String materia;

    private TurmaIdDTOOutput turma;

    private String anoLetivo;

}
