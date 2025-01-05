package com.api.diario.api.frequencia.dto.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ListFrequenciaDTOInput {

    private List<FrequenciaDTOInput> chamadas;

    private Long trimestre_id;

    private LocalDate data;
}
