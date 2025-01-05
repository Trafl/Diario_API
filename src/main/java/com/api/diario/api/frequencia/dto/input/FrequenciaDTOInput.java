package com.api.diario.api.frequencia.dto.input;

import com.api.diario.domain.model.alunos.Chamada;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FrequenciaDTOInput {

    private Long aluno_id;
    private Chamada chamada;
}
