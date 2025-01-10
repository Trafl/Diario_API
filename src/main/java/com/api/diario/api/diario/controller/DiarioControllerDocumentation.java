package com.api.diario.api.diario.controller;

import com.api.diario.api.diario.dto.output.DiarioDTOOuput;
import com.api.diario.api.diario.dto.output.DiarioPageDTOOutput;
import com.api.diario.domain.model.usuarios.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Diarios", description = "Controlador da entidade Diario")
public interface DiarioControllerDocumentation {

    @Operation(summary = "Lista Diarios por filtros",
            description = "Se logar como professor so podera ver os diarios desse professor, caso logue como diretor e possivel ver todos os diarios",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
            })
    ResponseEntity<PagedModel<EntityModel<DiarioPageDTOOutput>>> getDiarios(Long professorId, String anoLetivo, String materia, Long turmaId, Pageable pageable, CustomUserDetails userDetails);


    @Operation(summary = "Pega as informações de um diario",
            description = "Pega as informações detalhadas do diario",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),

                    @ApiResponse(responseCode = "400", description = "Erro nos campos digitados",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "403", description = "Não autorizado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "404", description = "Diario de id: xx não foi encontrado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail")))
            })
    ResponseEntity<DiarioDTOOuput> getOneDiario(Long diarioId) ;

    @Operation(summary = "Recupera ano letivo dos Diarios cadastrados ao professor",
            description = "Recupera somente o ano letivo dos Diarios ja cadastrados, usar para popular o dropDown",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
            })
    ResponseEntity<List<String>> getAnosLetivos(CustomUserDetails userDetails);
}
