package com.api.diario.domain.services.aluno;

import com.api.diario.domain.model.alunos.Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AlunoService {

    Aluno addAluno(Aluno aluno);

    Aluno getOneAluno(Long id);

    Page<Aluno> listAlunos(String nome, String numeroMatricula, String status, Boolean isPcd, Long turma_id, Pageable pageable);

    Aluno updateAluno(Long id, Aluno aluno);

    void disableAluno(Long id);

    void TransferAluno(Long id);
}
