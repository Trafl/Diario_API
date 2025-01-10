package com.api.diario.api.frequencia.mapper;

import com.api.diario.api.frequencia.dto.output.FrequenciaDTOOutput;
import com.api.diario.api.frequencia.dto.output.FrequenciaPageDTOOutput;
import com.api.diario.domain.model.frequencias.Frequencia;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FrequenciaMapper {

    private final ModelMapper mapper;

    public Page<FrequenciaPageDTOOutput> toPageDto (Page<Frequencia> frequencias){
        return frequencias.map(frequencia -> mapper.map(frequencia, FrequenciaPageDTOOutput.class));
    }

    public FrequenciaDTOOutput toDto(Frequencia frequencia) {
        return mapper.map(frequencia, FrequenciaDTOOutput.class);
    }
}
