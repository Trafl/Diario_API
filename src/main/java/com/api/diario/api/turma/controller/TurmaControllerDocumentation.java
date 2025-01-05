package com.api.diario.api.turma.controller;

import com.api.diario.api.turma.dto.input.ListOfAlunosDTOInput;
import com.api.diario.api.turma.dto.input.TurmaDTOInput;
import com.api.diario.api.turma.dto.output.TurmaDTOOutput;
import com.api.diario.api.turma.dto.output.TurmaPageDTOOutput;
import com.api.diario.domain.model.turma.Turno;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Turmas", description = "Controlador da entidade Turma")
public interface TurmaControllerDocumentation {
    @Operation(summary = "Lista turmas por filtros",
            description = "Aceita os parametros numero, turno, anoLetivo, alunoId, diarioId",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
            })
    public ResponseEntity<PagedModel<EntityModel<TurmaPageDTOOutput>>> listTurmas(String numero, Turno turno, Integer anoLetivo, Long alunoId, Long diarioId, Pageable pageable);

    @Operation(summary = "Pega as informações de uma turma",
            description = "Pega as informações detalhadas da Turma",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),

                    @ApiResponse(responseCode = "400", description = "Erro nos campos digitados",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "403", description = "Não autorizado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "404", description = "Turma de id: xx não foi encontrada",
                            content = @Content(schema = @Schema(ref = "ProblemDetail")))
            })
    public ResponseEntity<TurmaDTOOutput> getOneTurma(Long turma_id);

    @Operation(summary = "Adiciona uma Turma",
            description = "Não e necessario Alunos para criar",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Criado com sucesso"),

                    @ApiResponse(responseCode = "400", description = "Erro nos campos digitados",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "403", description = "Não autorizado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail")))
            })
    public ResponseEntity<TurmaDTOOutput> addTurma(TurmaDTOInput input);

    @Operation(summary = "Desabilita um aluno",
            description = "Troca o status para INATIVO",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),

                    @ApiResponse(responseCode = "400", description = "Erro nos campos digitados",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "403", description = "Não autorizado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "404", description = "Turma de id: xx não foi encontrada",
                            content = @Content(schema = @Schema(ref = "ProblemDetail")))
            })
    public ResponseEntity<TurmaDTOOutput> updateTurma(Long turma_id,TurmaDTOInput input);

    @Operation(summary = "Adiciona ou transfere um aluno de turma",
            description = "Função que adiciona um aluno a uma turma e tambem transfere ele",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transferido com sucesso"),

                    @ApiResponse(responseCode = "400", description = "Erro nos campos digitados",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "403", description = "Não autorizado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "404", description = "Turma de id: xx não foi encontrada",
                            content = @Content(schema = @Schema(ref = "ProblemDetail")))
            })
    public ResponseEntity<TurmaDTOOutput> addAndTransferAluno(Long turma_id, ListOfAlunosDTOInput input);
}
