package com.api.diario.domain.model.trimestre;

import com.api.diario.domain.model.frequencias.Frequencia;
import com.api.diario.domain.model.diario.Diario;
import com.api.diario.domain.model.instrumento.Instrumento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trimestre")
public class Trimestre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diario_id", nullable = false)
    private Diario diario;

    private Integer aulasAgendadas;

    @ElementCollection
    @CollectionTable(name = "aulas_realizadas", joinColumns = @JoinColumn(name = "trimestre_id"))
    @Column(name = "data")
    private List<LocalDate> aulasRealizadas;

    @OneToMany(mappedBy = "trimestre", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Instrumento> instrumentos;

    @OneToMany(mappedBy = "trimestre", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Frequencia> frequencias;

}
