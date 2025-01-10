package com.api.diario.api.frequencia.controller;

import com.api.diario.api.frequencia.dto.input.FrequenciaUpdateDTOInput;
import com.api.diario.api.frequencia.dto.input.ListFrequenciaDTOInput;
import com.api.diario.api.frequencia.dto.output.FrequenciaDTOOutput;
import com.api.diario.api.frequencia.dto.output.FrequenciaPageDTOOutput;
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

@Tag(name = "Frequencias", description = "Controlador da entidade Frequencia")
public interface FrequenciaControllerDocumentation {

    @Operation(summary = "Lista frequencias por filtros",
            description = "Aceita os parametros alunoId, turmaId, anoLetivo, trimestreId, startDate, endDate",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
            })
    ResponseEntity<PagedModel<EntityModel<FrequenciaPageDTOOutput>>> listFrequencias(Long alunoId, Long turmaId, String anoLetivo, Long trimestreId, LocalDate startDate, LocalDate endDate, Pageable pageable);


    @Operation(summary = "Pega as informações de uma frequencia",
            description = "Pega as informações detalhadas da frequencia",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),

                    @ApiResponse(responseCode = "400", description = "Erro nos campos digitados",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "403", description = "Não autorizado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "404", description = "Frequencia de id: xx não foi encontrada",
                            content = @Content(schema = @Schema(ref = "ProblemDetail")))
            })
    ResponseEntity<FrequenciaDTOOutput> getOneFrequencias(Long frequenciaId);

    @Operation(summary = "Atualiza uma frequencia",
            description = "Atualiza as informações da frequencia",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),

                    @ApiResponse(responseCode = "400", description = "Erro nos campos digitados",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "403", description = "Não autorizado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "404", description = "Frequencia de id: xx não foi encontrado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail")))
            })
    ResponseEntity<FrequenciaDTOOutput> updateOneFrequencias(Long frequenciaId,  FrequenciaUpdateDTOInput updateDTOInput);


    @Operation(summary = "Realiza a chamada",
            description = "Registra as chamadas dos alunos da turma em lote",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),

                    @ApiResponse(responseCode = "400", description = "Erro nos campos digitados",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "403", description = "Não autorizado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "404", description = "Frequencia de id: xx não foi encontrado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail")))
            })
    ResponseEntity makeCall(ListFrequenciaDTOInput listFrequenciaDTOInput);
}
