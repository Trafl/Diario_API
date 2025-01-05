package com.api.diario.api.turma.mapper;

import com.api.diario.api.turma.dto.input.TurmaDTOInput;
import com.api.diario.api.turma.dto.output.TurmaDTOOutput;
import com.api.diario.api.turma.dto.output.TurmaPageDTOOutput;
import com.api.diario.domain.model.turma.Turma;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TurmaMapper {

    private final ModelMapper mapper;

    public void updateTurma(Turma turmaSrc, Turma turmaTarget){
         mapper.map(turmaSrc, turmaTarget);
    }

    public TurmaDTOOutput toDTO(Turma turma){
        return mapper.map(turma, TurmaDTOOutput.class);
    }

    public Page<TurmaPageDTOOutput> toPageDTO(Page<Turma> turmas){
        return turmas.map(turma -> mapper.map(turma, TurmaPageDTOOutput.class));
    }

    public Turma toModel(TurmaDTOInput input) {
        return mapper.map(input, Turma.class);
    }
}
