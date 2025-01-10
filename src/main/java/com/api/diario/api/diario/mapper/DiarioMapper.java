package com.api.diario.api.diario.mapper;

import com.api.diario.api.diario.dto.output.DiarioDTOOuput;
import com.api.diario.api.diario.dto.output.DiarioPageDTOOutput;
import com.api.diario.domain.model.diario.Diario;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiarioMapper {

    private final ModelMapper mapper;

    public Page<DiarioPageDTOOutput> toPageDTO(Page<Diario> diarios){
        return diarios.map(diario -> mapper.map(diario,DiarioPageDTOOutput.class ));
    }

    public DiarioDTOOuput toDTO(Diario diario) {
        return mapper.map(diario,DiarioDTOOuput.class);
    }
}
