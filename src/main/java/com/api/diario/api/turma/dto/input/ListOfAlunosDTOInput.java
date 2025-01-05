package com.api.diario.api.turma.dto.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ListOfAlunosDTOInput {

    private List<ListAlunoIdDTOInput> alunos;
}
