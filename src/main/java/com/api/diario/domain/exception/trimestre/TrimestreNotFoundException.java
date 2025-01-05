package com.api.diario.domain.exception.trimestre;

public class TrimestreNotFoundException extends RuntimeException{
    public TrimestreNotFoundException(Long id) {
        super(String.format("Trimestre de Id %d não foi encontrado", id));
    }
}
