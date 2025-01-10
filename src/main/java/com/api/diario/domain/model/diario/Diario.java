package com.api.diario.domain.model.diario;

import com.api.diario.domain.model.trimestre.Trimestre;
import com.api.diario.domain.model.turma.Turma;
import com.api.diario.domain.model.usuarios.ProfessorRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "diario")
public class Diario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private ProfessorRole professor;

    private String materia;

    @OneToMany(mappedBy = "diario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Trimestre> trimestre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    private String anoLetivo;

    private LocalDate dataCriacao;
}
