package com.api.diario.api.turma.dto.input;

import com.api.diario.domain.model.turma.Turno;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class TurmaDTOInput {

    @NotNull
    @NotBlank
    private String numero;

    @NotNull
    private Turno turno;

    @NotNull
    private Integer anoLetivo;


}
