package com.api.diario.domain.services.nota;

import com.api.diario.domain.model.alunos.Nota;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotaService {
    Page<Nota>getNotas(Long alunoId, Long turmaId, String anoLetivo, Long trimestreId, Long instrumentoId, Pageable pageable);
}
