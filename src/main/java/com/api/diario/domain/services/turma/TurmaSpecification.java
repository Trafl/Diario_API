package com.api.diario.domain.services.turma;

import com.api.diario.domain.model.alunos.Aluno;
import com.api.diario.domain.model.diario.Diario;
import com.api.diario.domain.model.turma.Turma;
import com.api.diario.domain.model.turma.Turno;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class TurmaSpecification {

    public static Specification<Turma> hasNumero(String numero) {
        return (root, query, criteriaBuilder) ->
                numero != null ? criteriaBuilder.equal(root.get("numero"), numero) : null;
    }

    public static Specification<Turma> hasTurno(Turno turno) {
        return (root, query, criteriaBuilder) ->
                turno != null ? criteriaBuilder.equal(root.get("turno"), turno) : null;
    }

    public static Specification<Turma> hasAnoLetivo(Integer anoLetivo) {
        return (root, query, criteriaBuilder) ->
                anoLetivo != null ? criteriaBuilder.equal(root.get("anoLetivo"), anoLetivo) : null;
    }

    public static Specification<Turma> hasAlunoWithId(Long alunoId) {
        return (root, query, criteriaBuilder) -> {
            if (alunoId == null) return null;
            Join<Turma, Aluno> alunoJoin = root.join("alunos", JoinType.INNER);
            return criteriaBuilder.equal(alunoJoin.get("id"), alunoId);
        };
    }

    public static Specification<Turma> hasDiarioWithId(Long diarioId) {
        return (root, query, criteriaBuilder) -> {
            if (diarioId == null) return null;
            Join<Turma, Diario> diarioJoin = root.join("diarios", JoinType.INNER);
            return criteriaBuilder.equal(diarioJoin.get("id"), diarioId);
        };
    }
}
