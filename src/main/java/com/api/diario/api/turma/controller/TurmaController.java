package com.api.diario.api.turma.controller;

import com.api.diario.api.turma.dto.input.ListOfAlunosDTOInput;
import com.api.diario.api.turma.dto.input.TurmaDTOInput;
import com.api.diario.api.turma.dto.output.TurmaDTOOutput;
import com.api.diario.api.turma.dto.output.TurmaPageDTOOutput;
import com.api.diario.api.turma.mapper.TurmaMapper;
import com.api.diario.domain.model.turma.Turno;
import com.api.diario.domain.services.turma.TurmaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("api/v1/turmas")
@RequiredArgsConstructor
public class TurmaController implements TurmaControllerDocumentation{

    private final TurmaService turmaService;
    private final TurmaMapper mapper;
    private final PagedResourcesAssembler<TurmaPageDTOOutput> pagedResourcesAssembler;

    String timestamp = LocalDateTime.now().toString();

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<TurmaPageDTOOutput>>> listTurmas(
            @RequestParam(required = false) String numero,
            @RequestParam(required = false) Turno turno,
            @RequestParam(required = false) Integer anoLetivo,
            @RequestParam(required = false) Long alunoId,
            @RequestParam(required = false) Long diarioId,
            Pageable pageable) {
        log.info("[{}] - [TurmaController] Request: GET, EndPoint: 'api/v1/turmas'", timestamp);

        var pageTurmas = turmaService.listTurmas(numero, turno, anoLetivo, alunoId, diarioId, pageable);
        var pageTurmasDto = mapper.toPageDTO(pageTurmas);
        var model = pagedResourcesAssembler.toModel(pageTurmasDto);

        return ResponseEntity.ok(model);
    }

    @GetMapping("/{turma_id}")
    public ResponseEntity<TurmaDTOOutput> getOneTurma(@PathVariable Long turma_id){
        log.info("[{}] - [TurmaController] Request: GET, EndPoint: 'api/v1/turmas/{}'", timestamp, turma_id);

        var turma = turmaService.getOneTurma(turma_id);

        return ResponseEntity.ok(mapper.toDTO(turma));
    }

    @PostMapping()
    public ResponseEntity<TurmaDTOOutput> addTurma(@Valid @RequestBody TurmaDTOInput input){
        log.info("[{}] - [TurmaController] Request: POST, EndPoint: 'api/v1/turmas'", timestamp);
        var turma = mapper.toModel(input);

        turma = turmaService.addTurma(turma);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(turma));
    }

    @PutMapping("/{turma_id}")
    public ResponseEntity<TurmaDTOOutput> updateTurma(@PathVariable Long turma_id, @Valid @RequestBody TurmaDTOInput input){
        log.info("[{}] - [TurmaController] Request: PUT, EndPoint: 'api/v1/turmas/{}'", timestamp, turma_id);
        var turma = mapper.toModel(input);
         turma = turmaService.updateTurma(turma_id, turma);

        return ResponseEntity.ok(mapper.toDTO(turma));
    }

    @PostMapping("/{turma_id}")
    public ResponseEntity<TurmaDTOOutput> addAndTransferAluno(@PathVariable Long turma_id, ListOfAlunosDTOInput input){
        log.info("[{}] - [TurmaController] Request: POST, EndPoint: 'api/v1/turmas/{}'", timestamp, turma_id);
        var turma = turmaService.addAlunosToTurma(turma_id, input);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(turma));
    }

    @GetMapping("/dropdown")
    public ResponseEntity<List<String>> getNumerosForDropDown(@RequestParam String anoLetivo, @RequestParam Long diarioId){
        log.info("[{}] - [TurmaController] Request: GET, EndPoint: 'api/v1/turmas/dropdown' ", timestamp);

        var numeros = turmaService.numerosForDropDown(anoLetivo, diarioId);

        return ResponseEntity.ok(numeros);
    }
}
