package com.api.diario.domain.services.aluno;

import com.api.diario.domain.model.alunos.Aluno;
import org.springframework.data.jpa.domain.Specification;

public class AlunoSpecifications {

    public static Specification<Aluno> hasNome(String nome){
        return ((root, query, criteriaBuilder) ->
                nome == null ? null : criteriaBuilder.like(root.get("nome"), "%"+ nome + "%"));
    }

    public static Specification<Aluno> hasNumeroMatricula(String numeroMatricula){
        return (root, query, criteriaBuilder) ->
            numeroMatricula == null ? null : criteriaBuilder.equal(root.get("numeroMatricula"), numeroMatricula);
    }

    public static Specification<Aluno> hasStatus(String status) {
        return (root, query, criteriaBuilder) ->
                status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Aluno> isPCD(Boolean pcd){
        return (root, query, criteriaBuilder) ->
                pcd == null ? null : criteriaBuilder.equal(root.get("isPcd"), pcd);
    }

    public static Specification<Aluno> hasTurmaId(Long turmaId){
        return (root, query, criteriaBuilder) ->
                turmaId == null ? null : criteriaBuilder.equal(root.get("turma_id"), turmaId);
    }
}

