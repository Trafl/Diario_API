package com.api.diario.domain.exception.login;

public class IncorrectPasswordException extends RuntimeException{

    public IncorrectPasswordException() {
        super("Senha informada esta incorreta");
    }
}
