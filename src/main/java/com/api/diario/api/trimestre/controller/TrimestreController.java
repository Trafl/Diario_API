package com.api.diario.api.trimestre.controller;

import com.api.diario.api.trimestre.dto.input.TrimestreDTOInput;
import com.api.diario.api.trimestre.dto.output.TrimestreDTOOutput;
import com.api.diario.api.trimestre.dto.output.TrimestrePageDTOOutput;
import com.api.diario.api.trimestre.mapper.TrimestreMapper;
import com.api.diario.domain.services.trimestre.TrimestreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/trimestres")
public class TrimestreController implements TrimestreControllerDocumentation {

    private final TrimestreService service;

    private final TrimestreMapper mapper;

    private final PagedResourcesAssembler<TrimestrePageDTOOutput> pagedResourcesAssembler;

    @GetMapping()
    public ResponseEntity<PagedModel<EntityModel<TrimestrePageDTOOutput>>> listTrimestres(String nome,
                                                                            @RequestParam(required = false)Long diarioId,
                                                                            @RequestParam(required = false)String anoLetivo,
                                                                            @RequestParam(required = false)String turno,
                                                                            @RequestParam(required = false)String numeroTurma,
                                                                            @RequestParam(required = false)String instrumentoNome,
                                                                            @RequestParam(required = false)LocalDate startDate,
                                                                            @RequestParam(required = false)LocalDate endDate,
                                                                            @RequestParam(required = false)Long professorId,
                                                                            @PageableDefault Pageable pageable) {

        var trimestresPage = service.getTrimestres(nome,  diarioId,  anoLetivo,  turno,  numeroTurma,  instrumentoNome,  startDate,  endDate,  professorId, pageable);
        var trimetresPageDto = mapper.toPageDTO(trimestresPage);

        var model = pagedResourcesAssembler.toModel(trimetresPageDto);

        return ResponseEntity.ok(model);

    }

    @GetMapping("/{trimestreId}")
    public ResponseEntity<TrimestreDTOOutput> getOneTrimestre(@PathVariable Long trimestreId){

        var trimestre = service.getOneTrimestre(trimestreId);

        return ResponseEntity.ok(mapper.toDTO(trimestre));
    }


    @PostMapping()
    public ResponseEntity<TrimestreDTOOutput> addTrimestre(@RequestBody @Valid TrimestreDTOInput trimestreInput){

        var trimestre = service.addTrimestre(mapper.toModel(trimestreInput));

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(trimestre));
    }
}
