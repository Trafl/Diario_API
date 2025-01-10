package com.api.diario.api.diario.controller;

import com.api.diario.api.diario.dto.output.DiarioDTOOuput;
import com.api.diario.api.diario.dto.output.DiarioPageDTOOutput;
import com.api.diario.api.diario.mapper.DiarioMapper;
import com.api.diario.domain.model.diario.Diario;
import com.api.diario.domain.model.usuarios.CustomUserDetails;
import com.api.diario.domain.services.diario.DiarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1/diarios")
@RequiredArgsConstructor
public class DiarioController implements DiarioControllerDocumentation {

    private final DiarioService service;

    private final DiarioMapper mapper;

    private final PagedResourcesAssembler<DiarioPageDTOOutput> pagedResourcesAssembler;

    String timestamp = LocalDateTime.now().toString();

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<DiarioPageDTOOutput>>> getDiarios(
            @RequestParam(required = false) Long professorId,
            @RequestParam(required = false) String anoLetivo,
            @RequestParam(required = false) String materia,
            @RequestParam(required = false) Long turmaId,
            @PageableDefault Pageable pageable,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        log.info("[{}] - [DiarioController] Request: GET, EndPoint: 'api/v1/diarios'", timestamp);

        Page<Diario> diarios;

        if (userDetails.getAuthorities().stream().anyMatch(
                auth -> auth.getAuthority().equals("ROLE_PROFESSOR"))){
            professorId = userDetails.getId();
        }

        diarios = service.getDiarios(professorId, anoLetivo, materia, turmaId, pageable);

        var diariosDTO = mapper.toPageDTO(diarios);

        var pageDTO = pagedResourcesAssembler.toModel(diariosDTO);

        return ResponseEntity.ok(pageDTO);
    }

    @GetMapping("/{diarioId}")
    public ResponseEntity<DiarioDTOOuput> getOneDiario(@PathVariable Long diarioId) {
        log.info("[{}] - [DiarioController] Request: GET, EndPoint: 'api/v1/diarios/{}'", timestamp, diarioId);

        var diario = service.getOneDiario(diarioId);

        return ResponseEntity.ok(mapper.toDTO(diario));
    }

    @GetMapping("/anos-letivos/dropdown")
    public ResponseEntity<List<String>> getAnosLetivos(@AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("[{}] - [DiarioController] Request: GET, EndPoint: 'api/v1/diarios/anos-letivos/dropdown'", timestamp);

        List<String> anosLetivos = service.getAnosLetivos(userDetails.getId());

        return ResponseEntity.ok(anosLetivos);
    }

}
