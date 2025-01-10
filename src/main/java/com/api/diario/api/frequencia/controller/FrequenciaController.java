package com.api.diario.api.frequencia.controller;

import com.api.diario.api.frequencia.dto.input.FrequenciaUpdateDTOInput;
import com.api.diario.api.frequencia.dto.input.ListFrequenciaDTOInput;
import com.api.diario.api.frequencia.dto.output.FrequenciaDTOOutput;
import com.api.diario.api.frequencia.dto.output.FrequenciaPageDTOOutput;
import com.api.diario.api.frequencia.mapper.FrequenciaMapper;
import com.api.diario.domain.services.frequencia.FrequenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Log4j2
@RestController
@RequestMapping("/api/v1/frequencias")
@RequiredArgsConstructor
public class FrequenciaController implements FrequenciaControllerDocumentation {

    private final FrequenciaService service;

    private final FrequenciaMapper mapper;

    private final PagedResourcesAssembler<FrequenciaPageDTOOutput> pagedResourcesAssembler;

    String timestamp = LocalDateTime.now().toString();

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<FrequenciaPageDTOOutput>>> listFrequencias(@RequestParam(required = false) Long alunoId,
                                                                                            @RequestParam(required = false) Long turmaId,
                                                                                            @RequestParam(required = false) String anoLetivo,
                                                                                            @RequestParam(required = false) Long trimestreId,
                                                                                            @RequestParam(required = false) LocalDate startDate,
                                                                                            @RequestParam(required = false) LocalDate endDate,
                                                                                            @PageableDefault Pageable pageable){

        log.info("[{}] - [FrequenciaController] Request: GET, EndPoint: 'api/v1/frequencias'", timestamp);
        var frequenciasPage = service.getFrequencias(alunoId, turmaId, anoLetivo, trimestreId, startDate, endDate, pageable);
        var frequenciasPageDto = mapper.toPageDto(frequenciasPage);

        var model = pagedResourcesAssembler.toModel(frequenciasPageDto);

        return ResponseEntity.ok(model);
    }

    @GetMapping("/{frequenciaId}")
    public ResponseEntity<FrequenciaDTOOutput> getOneFrequencias(@PathVariable Long frequenciaId){
        log.info("[{}] - [FrequenciaController] Request: GET, EndPoint: 'api/v1/frequencias'", timestamp);
        var frequencia = service.getOneFrequencia(frequenciaId);

        return ResponseEntity.ok(mapper.toDto(frequencia));
    }

    @PutMapping("/{frequenciaId}")
    public ResponseEntity<FrequenciaDTOOutput> updateOneFrequencias(@PathVariable Long frequenciaId, @RequestBody FrequenciaUpdateDTOInput updateDTOInput){
        log.info("[{}] - [FrequenciaController] Request: PUT, EndPoint: 'api/v1/frequencias/{}'", timestamp, frequenciaId);

        var frequencia = service.updateFrequencia(frequenciaId, updateDTOInput);

        return ResponseEntity.ok(mapper.toDto(frequencia));
    }

    @PostMapping("/realizar-chamada")
    public ResponseEntity makeCall(@RequestBody @Valid ListFrequenciaDTOInput listFrequenciaDTOInput){
        log.info("[{}] - [FrequenciaController] Request: POST, EndPoint: 'api/v1/frequencias/realizar-chamada'", timestamp);
        service.makeCall(listFrequenciaDTOInput);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
