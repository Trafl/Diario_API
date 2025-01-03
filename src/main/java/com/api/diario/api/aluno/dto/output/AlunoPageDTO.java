package com.api.diario.api.aluno.dto.output;

import com.api.diario.domain.model.alunos.Frequencia;
import com.api.diario.domain.model.alunos.Nota;
import com.api.diario.domain.model.alunos.Status;
import com.api.diario.domain.model.turma.HistoricoTurma;
import com.api.diario.domain.model.turma.Turma;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
public class AlunoPageDTO {

    private Long id;

    private String nome;

    private String numeroMatricula;

    private Status status;

    private Boolean isPcd;

    private Long turma_id;
}
