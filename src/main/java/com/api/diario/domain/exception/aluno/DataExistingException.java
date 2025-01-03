package com.api.diario.domain.exception.aluno;

public class DataExistingException extends RuntimeException{
    public DataExistingException(String message) {
        super(String.format(message));
    }
}
