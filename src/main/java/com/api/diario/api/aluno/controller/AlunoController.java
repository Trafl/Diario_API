package com.api.diario.api.aluno.controller;

import com.api.diario.api.aluno.dto.input.AlunoDTOInput;
import com.api.diario.api.aluno.dto.input.AlunoUpdateDTOInput;
import com.api.diario.api.aluno.dto.output.AlunoOneDTOOutput;
import com.api.diario.api.aluno.dto.output.AlunoPageDTOOutput;
import com.api.diario.api.aluno.mapper.AlunoMapper;
import com.api.diario.domain.services.aluno.AlunoService;
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

import java.time.LocalDateTime;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/alunos")
public class AlunoController implements AlunoControllerDocumentation {

    private final AlunoService service;

    private final PagedResourcesAssembler<AlunoPageDTOOutput> pagedResourcesAssembler;

    private final AlunoMapper mapper;

    String timestamp = LocalDateTime.now().toString();

    @GetMapping()
    public ResponseEntity<PagedModel<EntityModel<AlunoPageDTOOutput>>> listAlunos(@RequestParam(required = false) String nome,
                                                                                  @RequestParam(required = false) String numeroMatricula,
                                                                                  @RequestParam(required = false) String status,
                                                                                  @RequestParam(required = false) Boolean isPcd,
                                                                                  @RequestParam(required = false) Long turma_id,
                                                                                  @PageableDefault Pageable pageable) {

        log.info("[{}] - [AlunoController] Request: GET, EndPoint: 'api/v1/alunos'", timestamp);

        var page =service.listAlunos(nome, numeroMatricula, status, isPcd, turma_id, pageable);

        var pageDto = mapper.PageToPageDTO(page);

        PagedModel<EntityModel<AlunoPageDTOOutput>> model = pagedResourcesAssembler.toModel(pageDto);

        return ResponseEntity.ok(model);
    }

    @GetMapping("/{alunoId}")
    public ResponseEntity<AlunoOneDTOOutput> getOneAluno(@PathVariable Long alunoId){
        log.info("[{}] - [AlunoController] Request: GET, EndPoint: 'api/v1/alunos/{}'", timestamp, alunoId);

        var aluno = service.getOneAluno(alunoId);

        return ResponseEntity.ok(mapper.ToDTO(aluno));
    }

    @PostMapping()
    public ResponseEntity<AlunoOneDTOOutput> addAluno(@RequestBody AlunoDTOInput alunoDto){
        log.info("[{}] - [AlunoController] Request: POST, EndPoint: 'api/v1/alunos'", timestamp);

        var aluno = mapper.toModel(alunoDto);

        aluno = service.addAluno(aluno);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.ToDTO(aluno));
    }

    @PutMapping("/{alunoId}")
    public ResponseEntity<AlunoOneDTOOutput> updateAluno(@PathVariable Long alunoId, @Valid @RequestBody AlunoUpdateDTOInput alunoDTOInput){
        var alunoUpdate = mapper.ToUpdateModel(alunoDTOInput);
        alunoUpdate = service.updateAluno(alunoId, alunoUpdate);
        return ResponseEntity.ok(mapper.ToDTO(alunoUpdate));
    }

    @PutMapping("/{alunoId}/desabilitar")
    public ResponseEntity<Void> disableAluno(@PathVariable Long alunoId){
        log.info("[{}] - [AlunoController] Request: PUT, EndPoint: 'api/v1/alunos/{}/desabilitar'", timestamp, alunoId);
        service.disableAluno(alunoId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{alunoId}/transferir")
    public ResponseEntity<Void> transferAluno(@PathVariable Long alunoId){
        log.info("[{}] - [AlunoController] Request: PUT, EndPoint: 'api/v1/alunos/{}/transferir'", timestamp, alunoId);
        service.transferAluno(alunoId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
