package com.api.diario.domain.services.trimestre;

import com.api.diario.domain.model.trimestre.Trimestre;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TrimestreSpecification {
    // Filtrar pelo nome do trimestre
    public static Specification<Trimestre> hasNome(String nome) {
        return (root, query, criteriaBuilder) ->
                nome == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    // Filtrar pelo ID do diário associado
    public static Specification<Trimestre> hasDiarioId(Long diarioId) {
        return (root, query, criteriaBuilder) ->
                diarioId == null ? null : criteriaBuilder.equal(root.get("diario").get("id"), diarioId);
    }

    // Filtrar pelo ano letivo (no diário)
    public static Specification<Trimestre> hasAnoLetivo(String anoLetivo) {
        return (root, query, criteriaBuilder) ->
                anoLetivo == null ? null : criteriaBuilder.equal(root.get("diario").get("anoLetivo"), anoLetivo);
    }

    // Filtrar pelo turno da turma
    public static Specification<Trimestre> hasTurno(String turno) {
        return (root, query, criteriaBuilder) ->
                turno == null ? null : criteriaBuilder.equal(root.get("diario").get("turma").get("turno"), turno);
    }

    // Filtrar pelo número da turma
    public static Specification<Trimestre> hasNumeroTurma(String numeroTurma) {
        return (root, query, criteriaBuilder) ->
                numeroTurma == null ? null : criteriaBuilder.equal(root.get("diario").get("turma").get("numero"), numeroTurma);
    }

    // Filtrar pelo nome do instrumento associado
    public static Specification<Trimestre> hasInstrumentoNome(String instrumentoNome) {
        return (root, query, criteriaBuilder) -> {
            if (instrumentoNome == null) return null;
            var joinInstrumento = root.join("instrumentos"); // Relacionamento entre Trimestre e Instrumento
            return criteriaBuilder.like(criteriaBuilder.lower(joinInstrumento.get("nome")), "%" + instrumentoNome.toLowerCase() + "%");
        };
    }

    // Filtrar por data de aulas realizadas (intervalo de datas)
    public static Specification<Trimestre> hasAulasRealizadasBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null || endDate == null) return null;
            var joinAulas = root.join("aulasRealizadas"); // Relacionamento entre Trimestre e AulasRealizadas
            return criteriaBuilder.between(joinAulas.get("data"), startDate, endDate);
        };
    }

    // Filtrar por professor associado ao diário
    public static Specification<Trimestre> hasProfessorId(Long professorId) {
        return (root, query, criteriaBuilder) ->
                professorId == null ? null : criteriaBuilder.equal(root.get("diario").get("professorId"), professorId);
    }


    public static Specification<Trimestre> createSpecification(String nome, Long diarioId, String anoLetivo, String turno, String numeroTurma, String instrumentoNome, LocalDate startDate, LocalDate endDate, Long professorId){

        return Specification
                .where(TrimestreSpecification.hasNome(nome))
                .and(TrimestreSpecification.hasDiarioId(diarioId))
                .and(TrimestreSpecification.hasAnoLetivo(anoLetivo))
                .and(TrimestreSpecification.hasTurno(turno))
                .and(TrimestreSpecification.hasNumeroTurma(numeroTurma))
                .and(TrimestreSpecification.hasInstrumentoNome(instrumentoNome))
                .and(TrimestreSpecification.hasAulasRealizadasBetween(startDate, endDate))
                .and(TrimestreSpecification.hasProfessorId(professorId));

    }
}
