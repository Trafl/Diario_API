package com.api.diario.domain.exception.turma;

public class TurmaNotFoundException extends RuntimeException{
    public TurmaNotFoundException(Long id) {
        super(String.format("Turma de Id %d não foi encontrada", id));
    }
}
