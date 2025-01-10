package com.api.diario.api.frequencia.dto.input;

import com.api.diario.domain.model.frequencias.Chamada;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FrequenciaUpdateDTOInput {

    private Chamada chamada;

}
