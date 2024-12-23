package com.api.diario.domain.exception.login;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String email) {
        super(String.format("Usuario com email %s não foi encontrado", email));
    }
}
