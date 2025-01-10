package com.api.diario.domain.services.frequencia;

import com.api.diario.domain.model.frequencias.Frequencia;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class FrequenciaSpecification {
    // Filtrar por ID do aluno
    public static Specification<Frequencia> byAlunoId(Long alunoId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("aluno").get("id"), alunoId);
    }

    // Filtrar por ID do trimestre (direto)
    public static Specification<Frequencia> byTrimestreId(Long trimestreId) {
        return (root, query, criteriaBuilder) ->
                trimestreId == null ? null : criteriaBuilder.equal(root.get("trimestre").get("id"), trimestreId);
    }

    // Filtrar por ano letivo relacionado ao diário no trimestre
    public static Specification<Frequencia> byAnoLetivo(String anoLetivo) {
        return (root, query, criteriaBuilder) ->
                anoLetivo == null ? null : criteriaBuilder.equal(root.get("trimestre").get("diario").get("anoLetivo"), anoLetivo);
    }

    // Filtrar por turma associada ao diário no trimestre
    public static Specification<Frequencia> byTurmaId(Long turmaId) {
        return (root, query, criteriaBuilder) ->
                turmaId == null ? null : criteriaBuilder.equal(root.get("trimestre").get("diario").get("turma").get("id"), turmaId);
    }

    // Filtrar por intervalo de datas
    public static Specification<Frequencia> byDataBetween(LocalDate start, LocalDate end) {
        return (root, query, criteriaBuilder) -> {
            if (start == null && end == null) {
                return null;
            } else if (start != null && end != null) {
                return criteriaBuilder.between(root.get("data"), start, end);
            } else if (start != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("data"), start);
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("data"), end);
            }
        };
    }
}
