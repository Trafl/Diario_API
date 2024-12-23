package com.api.diario.domain.exception.login;

public class ExistUserInDbException extends RuntimeException{
    public ExistUserInDbException(String email) {
        super(String.format("Já existe um usuario registrado com o email: %s", email));
    }
}
