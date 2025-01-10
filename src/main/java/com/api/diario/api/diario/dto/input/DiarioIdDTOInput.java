package com.api.diario.api.diario.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class DiarioIdDTOInput {

    @NotNull
    private Long id;
}
