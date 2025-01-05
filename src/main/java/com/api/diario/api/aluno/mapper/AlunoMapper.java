package com.api.diario.api.aluno.mapper;

import com.api.diario.api.aluno.dto.input.AlunoDTOInput;
import com.api.diario.api.aluno.dto.input.AlunoUpdateDTOInput;
import com.api.diario.api.aluno.dto.output.AlunoOneDTO;
import com.api.diario.api.aluno.dto.output.AlunoPageDTO;
import com.api.diario.domain.model.alunos.Aluno;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlunoMapper {

    private final ModelMapper mapper;

    public void updateAluno(Aluno alunoUpdate, Aluno alunoDb){
        mapper.map(alunoUpdate, alunoDb);
    }

    public Page<AlunoPageDTO> PageToPageDTO(Page<Aluno> page){
        return page.map(aluno -> mapper.map(aluno, AlunoPageDTO.class));
    }

    public AlunoOneDTO ToDTO(Aluno aluno){
        return mapper.map(aluno, AlunoOneDTO.class);
    }

    public Aluno ToUpdateModel(AlunoUpdateDTOInput alunoUpdate){
        return mapper.map(alunoUpdate, Aluno.class);
    }

    public Aluno toModel (AlunoDTOInput alunoDTOInput){
        return mapper.map(alunoDTOInput, Aluno.class);
    }
}
