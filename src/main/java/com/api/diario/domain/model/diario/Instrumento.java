package com.api.diario.domain.model.diario;

import com.api.diario.domain.model.alunos.Nota;
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
@Table(name = "instrumento")
public class Instrumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String tipo;

    private Float valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trimestre_id", nullable = false)
    private Trimestre trimestre;

    @OneToOne(mappedBy = "instrumento", cascade = CascadeType.ALL, orphanRemoval = true)
    private Nota nota;
}
