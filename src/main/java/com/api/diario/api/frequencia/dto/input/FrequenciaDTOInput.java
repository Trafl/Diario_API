package com.api.diario.api.frequencia.dto.input;

import com.api.diario.domain.model.frequencias.Chamada;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FrequenciaDTOInput {

    @NotNull
    private Long aluno_id;

    @NotNull
    private Chamada chamada;
}
