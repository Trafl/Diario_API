package com.api.diario.domain.exception.login;

public class ExistUserInDbException extends RuntimeException{
    public ExistUserInDbException(String message) {
        super(message);
    }
}
