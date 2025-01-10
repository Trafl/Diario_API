package com.api.diario.api.frequencia.dto.input;

import com.api.diario.domain.model.frequencias.Chamada;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class FrequenciaUpdateDTOInput {

    private Chamada chamada;

}
