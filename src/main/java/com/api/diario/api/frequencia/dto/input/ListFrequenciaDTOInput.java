package com.api.diario.api.frequencia.dto.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ListFrequenciaDTOInput {

    @NotNull
    @Valid
    private List<FrequenciaDTOInput> chamadas;

    @NotNull
    private Long trimestre_id;

    @NotNull
    private LocalDate data;
}
