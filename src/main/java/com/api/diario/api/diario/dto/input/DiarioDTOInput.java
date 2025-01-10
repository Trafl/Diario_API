package com.api.diario.api.diario.dto.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiarioDTOInput {

    @NotNull
    private Long professorId;

    @NotNull
    @Size(max = 100)
    private String materia;

    private Long turmaId;

    @NotNull
    @Pattern(regexp = "\\d{4}(\\/\\d{4})?")
    private String anoLetivo;

}
