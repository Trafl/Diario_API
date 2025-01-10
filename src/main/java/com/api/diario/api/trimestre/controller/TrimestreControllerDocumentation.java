package com.api.diario.api.trimestre.controller;

import com.api.diario.api.trimestre.dto.input.TrimestreDTOInput;
import com.api.diario.api.trimestre.dto.output.TrimestreDTOOutput;
import com.api.diario.api.trimestre.dto.output.TrimestrePageDTOOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

@Tag(name = "Trimestres", description = "Controlador da entidade Trimestre")
public interface TrimestreControllerDocumentation {

    @Operation(summary = "Lista trimestres por filtros",
            description = "Aceita os parametros nome, diarioId, anoLetivo, turno, numeroTurma, instrumentoNome, startDate, endDate, professorId",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
            })
    ResponseEntity<PagedModel<EntityModel<TrimestrePageDTOOutput>>> listTrimestres(String nome, Long diarioId, String anoLetivo, String turno, String numeroTurma, String instrumentoNome, LocalDate startDate, LocalDate endDate, Long professorId, Pageable pageable);

    @Operation(summary = "Pega as informações de um trimestre",
            description = "Pega as informações detalhadas do trimestre porem não pega intrumento e frequencia",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),

                    @ApiResponse(responseCode = "400", description = "Erro nos campos digitados",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "403", description = "Não autorizado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "404", description = "Trimestre de id: xx não foi encontrado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail")))
            })
    ResponseEntity<TrimestreDTOOutput> getOneTrimestre(Long trimestreId);

    @Operation(summary = "Adiciona um Trimestre",
                      responses = {
                    @ApiResponse(responseCode = "201", description = "Criado com sucesso"),

                    @ApiResponse(responseCode = "400", description = "Erro nos campos digitados",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "403", description = "Não autorizado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail")))
            })
    ResponseEntity<TrimestreDTOOutput> addTrimestre(TrimestreDTOInput trimestreInput);
}
