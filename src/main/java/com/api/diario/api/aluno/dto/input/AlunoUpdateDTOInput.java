package com.api.diario.api.aluno.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlunoUpdateDTOInput {

    private String nome;

    private String numeroMatricula;

    private Boolean isPcd;

}
