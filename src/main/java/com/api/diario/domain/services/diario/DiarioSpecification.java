package com.api.diario.domain.services.diario;

import com.api.diario.domain.model.diario.Diario;
import org.springframework.data.jpa.domain.Specification;

public class DiarioSpecification {

    // Filtrar por ID do professor
    public static Specification<Diario> byProfessorId(Long professorId) {
        return (root, query, criteriaBuilder) ->
                professorId == null ? null : criteriaBuilder.equal(root.get("professor").get("id"), professorId);
    }

    // Filtrar por ano letivo
    public static Specification<Diario> byAnoLetivo(String anoLetivo) {
        return (root, query, criteriaBuilder) ->
                anoLetivo == null ? null : criteriaBuilder.equal(root.get("anoLetivo"), anoLetivo);
    }

    // Filtrar por matéria
    public static Specification<Diario> byMateria(String materia) {
        return (root, query, criteriaBuilder) ->
                materia == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("materia")), "%" + materia.toLowerCase() + "%");
    }

    // Filtrar por turma
    public static Specification<Diario> byTurmaId(Long turmaId) {
        return (root, query, criteriaBuilder) ->
                turmaId == null ? null : criteriaBuilder.equal(root.get("turma").get("id"), turmaId);
    }


    // Combinação de filtros dinâmicos
    public static Specification<Diario> createSpecification(Long professorId, String anoLetivo, String materia, Long turmaId) {
        return Specification
                .where(byProfessorId(professorId))
                .and(byAnoLetivo(anoLetivo))
                .and(byMateria(materia))
                .and(byTurmaId(turmaId));
    }
}
