package com.api.diario.domain.exception.aluno;

public class AlunoNotFoundException extends RuntimeException{
    public AlunoNotFoundException(Long id) {
        super(String.format("Aluno de Id %d não foi encontrado", id));
    }
}
