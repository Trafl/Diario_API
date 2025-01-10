package com.api.diario.api.trimestre.mapper;

import com.api.diario.api.trimestre.dto.input.TrimestreDTOInput;
import com.api.diario.api.trimestre.dto.output.TrimestreDTOOutput;
import com.api.diario.api.trimestre.dto.output.TrimestrePageDTOOutput;
import com.api.diario.domain.model.trimestre.Trimestre;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrimestreMapper {

    private final ModelMapper mapper;

    public TrimestreDTOOutput toDTO(Trimestre trimestre){
        return mapper.map(trimestre, TrimestreDTOOutput.class);
    }

    public Page<TrimestrePageDTOOutput> toPageDTO(Page<Trimestre> trimestres){
        return trimestres.map(trimestre -> mapper.map(trimestre, TrimestrePageDTOOutput.class));
    }

    public Trimestre toModel(TrimestreDTOInput trimestreDto){
        return mapper.map(trimestreDto, Trimestre.class);
    }
}
