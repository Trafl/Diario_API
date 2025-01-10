package com.api.diario.api.trimestre.dto.input;

import com.api.diario.api.diario.dto.input.DiarioIdDTOInput;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TrimestreDTOInput {

    @NotNull
    private String nome;

    @Valid
    private DiarioIdDTOInput diario;

    @NotNull
    private Integer aulasAgendadas;

}
