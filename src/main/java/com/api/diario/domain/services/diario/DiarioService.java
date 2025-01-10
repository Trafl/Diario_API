package com.api.diario.domain.services.diario;

import com.api.diario.domain.model.diario.Diario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DiarioService {

    Page<Diario> getDiarios(Long professorId, String anoLetivo, String materia, Long turmaId, Pageable pageable);

    Diario getOneDiario(Long diarioId);

    List<String> getAnosLetivos(Long professorId);

}
