package com.api.diario.domain.services.nota;

import com.api.diario.domain.model.notas.Nota;
import org.springframework.data.jpa.domain.Specification;

public class NotaSpecification {
    // Filtrar por ID do aluno
    public static Specification<Nota> byAlunoId(Long alunoId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("aluno").get("id"), alunoId);
    }

    // Filtrar por ID do instrumento
    public static Specification<Nota> byInstrumentoId(Long instrumentoId) {
        return (root, query, criteriaBuilder) ->
                instrumentoId == null ? null : criteriaBuilder.equal(root.get("instrumento").get("id"), instrumentoId);
    }

    // Filtrar por ano letivo associado ao diário no trimestre do instrumento
    public static Specification<Nota> byAnoLetivo(String anoLetivo) {
        return (root, query, criteriaBuilder) ->
                anoLetivo == null ? null : criteriaBuilder.equal(root.get("instrumento").get("trimestre").get("diario").get("anoLetivo"), anoLetivo);
    }

    // Filtrar por turma associada ao diário no trimestre do instrumento
    public static Specification<Nota> byTurmaId(Long turmaId) {
        return (root, query, criteriaBuilder) ->
                turmaId == null ? null : criteriaBuilder.equal(root.get("instrumento").get("trimestre").get("diario").get("turma").get("id"), turmaId);
    }

    // Filtrar por trimestre associado ao diário
    public static Specification<Nota> byTrimestreId(Long trimestreId) {
        return (root, query, criteriaBuilder) ->
                trimestreId == null ? null : criteriaBuilder.equal(root.get("instrumento").get("trimestre").get("id"), trimestreId);
    }
}
