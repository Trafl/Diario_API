package com.api.diario.domain.exception.historicoturma;

public class HistoricoNotFoundException extends RuntimeException{
    public HistoricoNotFoundException(Long id) {
        super(String.format("Historico de turmas do aluno com Id %d não foi encontrada", id));
    }
}
