package com.api.diario.api.diario.dto.output;

import com.api.diario.api.auth.dto.RoleDTOOutput;
import com.api.diario.api.trimestre.dto.output.TrimestreIdDTOOutput;
import com.api.diario.api.turma.dto.output.TurmaSummaryDTOOutput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DiarioDTOOuput {

    private Long id;

    private RoleDTOOutput professor;

    private String materia;

    private Set<TrimestreIdDTOOutput> trimestre;

    private TurmaSummaryDTOOutput turma;

    private String anoLetivo;

}
