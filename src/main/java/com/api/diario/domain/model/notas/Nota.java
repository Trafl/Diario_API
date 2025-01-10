package com.api.diario.domain.model.notas;

import com.api.diario.domain.model.alunos.Aluno;
import com.api.diario.domain.model.instrumento.Instrumento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "nota")
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float nota;

    private Float notaRecuperacao;

    private Float notaMaxima;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instrumento_id", nullable = false)
    private Instrumento instrumento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;
}
