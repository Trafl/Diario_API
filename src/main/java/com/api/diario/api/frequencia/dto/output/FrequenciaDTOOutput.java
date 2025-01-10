package com.api.diario.api.frequencia.dto.output;

import com.api.diario.api.aluno.dto.output.AlunoIdDTOOutput;
import com.api.diario.api.trimestre.dto.output.TrimestreIdDTOOutput;
import com.api.diario.domain.model.frequencias.Chamada;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class FrequenciaDTOOutput {

    private Long id;

    private LocalDate data;

    private Chamada chamada;

    private AlunoIdDTOOutput aluno;

    private TrimestreIdDTOOutput trimestre;
}
