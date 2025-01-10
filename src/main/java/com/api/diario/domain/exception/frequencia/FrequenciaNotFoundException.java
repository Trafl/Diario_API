package com.api.diario.domain.exception.frequencia;

public class FrequenciaNotFoundException extends RuntimeException {
    public FrequenciaNotFoundException(Long frequenciaId) {
        super(String.format("Frenquencia de id %d não foi encontrado no sistema", frequenciaId));
    }
}
