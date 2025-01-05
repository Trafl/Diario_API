package com.api.diario.api.aluno.controller;

import com.api.diario.api.aluno.dto.input.AlunoDTOInput;
import com.api.diario.api.aluno.dto.input.AlunoUpdateDTOInput;
import com.api.diario.api.aluno.dto.output.AlunoOneDTO;
import com.api.diario.api.aluno.dto.output.AlunoPageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Alunos", description = "Controlador da entidade Aluno")
public interface AlunoControllerDocumentation {

    @Operation(summary = "Lista alunos por filtros",
            description = "Aceita os parametros Nome, Numero da matricula, Status, PCD, Id da turma",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
            })
    ResponseEntity<PagedModel<EntityModel<AlunoPageDTO>>> listAlunos(String nome,String numeroMatricula, String status,Boolean isPcd,Long turma_id, Pageable pageable);

    @Operation(summary = "Pega as informações de um aluno",
            description = "Pega as informações detalhadas do aluno porem não pega nota e frequencia",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),

                    @ApiResponse(responseCode = "400", description = "Erro nos campos digitados",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "403", description = "Não autorizado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "404", description = "Aluno de id: xx não foi encontrado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail")))
            })
    ResponseEntity<AlunoOneDTO> getOneAluno( Long alunoId);


    @Operation(summary = "Adiciona um Aluno",
            description = "Não e necessario uma turma para criar",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Criado com sucesso"),

                    @ApiResponse(responseCode = "400", description = "Erro nos campos digitados",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "403", description = "Não autorizado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail")))
            })
    ResponseEntity<AlunoOneDTO> addAluno(AlunoDTOInput alunoDto);


    @Operation(summary = "Atualiza um aluno",
            description = "Atualiza as informações do aluno",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),

                    @ApiResponse(responseCode = "400", description = "Erro nos campos digitados",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "403", description = "Não autorizado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "404", description = "Aluno de id: xx não foi encontrado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail")))
            })
    ResponseEntity<AlunoOneDTO> updateAluno( Long alunoId, AlunoUpdateDTOInput alunoDTOInput);

    @Operation(summary = "Desabilita um aluno",
            description = "Troca o status para INATIVO",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Desabilitado com sucesso"),

                    @ApiResponse(responseCode = "400", description = "Erro nos campos digitados",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "403", description = "Não autorizado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "404", description = "Aluno de id: xx não foi encontrado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail")))
            })
    ResponseEntity<Void> disableAluno(Long alunoId);



    @Operation(summary = "Transfere um aluno de escola",
            description = "Troca o status para TRANSFERIDO",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Transferido com sucesso"),

                    @ApiResponse(responseCode = "400", description = "Erro nos campos digitados",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "403", description = "Não autorizado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail"))),

                    @ApiResponse(responseCode = "404", description = "Aluno de id: xx não foi encontrado",
                            content = @Content(schema = @Schema(ref = "ProblemDetail")))
            })
    ResponseEntity<Void> transferAluno( Long alunoId);
}
