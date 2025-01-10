package com.api.diario.api.trimestre.dto.output;

import com.api.diario.api.diario.dto.output.DiarioIdDTOOuput;
import com.api.diario.domain.model.frequencias.Frequencia;
import com.api.diario.domain.model.instrumento.Instrumento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TrimestreDTOOutput {

    private Long id;

    private String nome;

    private DiarioIdDTOOuput diario;

    private Integer aulasAgendadas;

    private List<LocalDate> aulasRealizadas;

    private Set<Instrumento> instrumentos;

    private Set<Frequencia> frequencias;

}
