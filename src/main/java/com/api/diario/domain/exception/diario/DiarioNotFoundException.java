package com.api.diario.domain.exception.diario;

public class DiarioNotFoundException extends RuntimeException{
    public DiarioNotFoundException(Long id) {
        super(String.format("Diario de Id %d não foi encontrado", id));
    }
}
