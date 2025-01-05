package com.api.diario.services.aluno;

import com.api.diario.api.aluno.mapper.AlunoMapper;
import com.api.diario.domain.exception.aluno.AlunoNotFoundException;
import com.api.diario.domain.exception.aluno.DataExistingException;
import com.api.diario.domain.exception.historicoturma.HistoricoNotFoundException;
import com.api.diario.domain.model.alunos.Aluno;
import com.api.diario.domain.model.alunos.Status;
import com.api.diario.domain.model.turma.HistoricoTurma;
import com.api.diario.domain.model.turma.Turma;
import com.api.diario.domain.repository.AlunoRepository;
import com.api.diario.domain.services.aluno.AlunoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlunoServiceImplTest {
    @InjectMocks
    private AlunoServiceImpl service;

    @Mock
    private  AlunoRepository repository;

    @Mock
    private  AlunoMapper mapper;

    private Aluno aluno;

    private HistoricoTurma historicoTurma;

    private Pageable pageable;

    @BeforeEach
    void setUp(){
        aluno = new Aluno();
        aluno.setNome("Test Student");
        aluno.setNumeroMatricula("12345");
        aluno.setIsPcd(false);

        pageable = PageRequest.of(0, 10);

        historicoTurma = new HistoricoTurma();
        historicoTurma.setId(1L);
        historicoTurma.setDataInicio(LocalDate.of(2023, 1, 1));
        historicoTurma.setDataFim(null);
        historicoTurma.setTurma(new Turma());
        aluno.setHistoricoTurmas(new ArrayList<>(List.of(historicoTurma)));
    }

    @Nested
    class addAluno {

        @Test
        @DisplayName("Adiciona um aluno")
        public void addAlunoInDb(){
            aluno.setId(1L);
            given(service.addAluno(aluno)).willReturn(aluno);

            var savedAluno = service.addAluno(aluno);

            assertNotNull(savedAluno);
            assertEquals("Test Student", savedAluno.getNome());
            assertEquals("12345", savedAluno.getNumeroMatricula());
            assertEquals(Status.ATIVO, savedAluno.getStatus());
            assertEquals(new HashSet<>(), savedAluno.getFrequencias());
            assertEquals(new HashSet<>(), savedAluno.getNotas());
            assertEquals(new ArrayList<>(), savedAluno.getHistoricoTurmas());
        }

        @Test
        @DisplayName("Adiciona um aluno com matricula repetida e dispara DataExistingException ")
        public void addAlunoWithExistentMatriculaThrowDataExistingException(){

            given(repository.save(any(Aluno.class))).willThrow(DataIntegrityViolationException.class);

            var result = assertThrows(DataExistingException.class,
                    () ->{
                        service.addAluno(aluno);
                    } );

            assertEquals("Dados já existentes no sistema.", result.getMessage());
        }
    }

    @Nested
    class getOneAluno {

        @Test
        @DisplayName("Pesquisando aluno com Id inexistente e lançando AlunoNotFoundException")
        public void getAlunoWithWhongIdThrowAlunoNotFoundException(){

            var invalidId = 1L;
            var exception = new AlunoNotFoundException(invalidId);

            given(repository.findById(1L)).willThrow(exception);

            var result = assertThrows(AlunoNotFoundException.class,
                    () -> service.getOneAluno(1L)
            );

            assertEquals("Aluno de Id 1 não foi encontrado", result.getMessage());
        }

    }


    @Nested
    class listAlunos{

        @Test
        @DisplayName("Deve retornar uma página de alunos filtrados por critérios")
        public void listAlunosWithFilters() {
            Page<Aluno> pageResult = new PageImpl<>(List.of(aluno), pageable, 1);

            given(repository.findAll(any(Specification.class), eq(pageable))).willReturn(pageResult);

            var result = service.listAlunos("Test", "12345", "Ativo", false, 1L, pageable);

            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
            assertEquals("Test Student", result.getContent().get(0).getNome());
            assertEquals("12345", result.getContent().get(0).getNumeroMatricula());
        }

        @Test
        @DisplayName("Deve retornar uma página vazia quando não houver correspondência com os critérios")
        public void listAlunosWithNoMatches() {

            Page<Aluno> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

            given(repository.findAll(any(Specification.class), eq(pageable))).willReturn(emptyPage);

            var result = service.listAlunos("NonExistent", "00000", "Inativo", true, 1L, pageable);

            assertNotNull(result);
            assertEquals(0, result.getTotalElements());
            assertTrue(result.getContent().isEmpty());
        }

    }

    @Nested
    class updateAluno{

        @Test
        @DisplayName("Atualiza um aluno com sucesso")
        public void updateAlunoSuccessfully() {

            Aluno alunoInDb;
            Aluno alunoUpdate;

            alunoInDb = new Aluno();
            alunoInDb.setId(1L);
            alunoInDb.setNome("João Silva");
            alunoInDb.setNumeroMatricula("12345");
            alunoInDb.setStatus(Status.ATIVO);
            alunoInDb.setIsPcd(false);

            alunoUpdate = new Aluno();
            alunoUpdate.setNome("João Pedro");
            alunoUpdate.setNumeroMatricula("54321");
            alunoUpdate.setStatus(Status.INATIVO);
            alunoUpdate.setIsPcd(true);

            // Mock do aluno existente
            given(repository.findById(1L)).willReturn(Optional.of(alunoInDb));

            // Mock do mapeamento de atualização
            doAnswer(invocation -> {
                Aluno source = invocation.getArgument(0);
                Aluno target = invocation.getArgument(1);
                target.setNome(source.getNome());
                target.setNumeroMatricula(source.getNumeroMatricula());
                target.setStatus(source.getStatus());
                target.setIsPcd(source.getIsPcd());
                return null;
            }).when(mapper).updateAluno(any(Aluno.class), any(Aluno.class));

            // Mock do salvamento
            given(repository.save(alunoInDb)).willReturn(alunoInDb);

            // Execução do método
            var updatedAluno = service.updateAluno(1L, alunoUpdate);

            // Verificações
            assertNotNull(updatedAluno);
            assertEquals("João Pedro", updatedAluno.getNome());
            assertEquals("54321", updatedAluno.getNumeroMatricula());
            assertEquals(Status.INATIVO, updatedAluno.getStatus());
            assertTrue(updatedAluno.getIsPcd());

            // Verificar interações
            verify(repository).findById(1L);
            verify(mapper).updateAluno(alunoUpdate, alunoInDb);
            verify(repository).save(alunoInDb);
        }

        @Test
        @DisplayName("Lança exceção AlunoNotFoundException ao tentar atualizar um aluno inexistente")
        public void updateAlunoThrowsAlunoNotFoundException() {
            // Mock de aluno inexistente
            given(repository.findById(1L)).willReturn(Optional.empty());

            // Execução do método e validação da exceção
            assertThrows(AlunoNotFoundException.class, () -> service.updateAluno(1L, aluno));

            // Verificar interações
            verify(repository).findById(1L);
            verifyNoMoreInteractions(mapper, repository);
        }
    }

    @Nested
    class DisableAluno {

        @Test
        @DisplayName("Desabilita um aluno ativo com histórico ativo")
        void shouldDisableAlunoAndCloseHistorico() {
            given(repository.findById(1L)).willReturn(Optional.of(aluno));

            service.disableAluno(1L);

            assertEquals(Status.INATIVO, aluno.getStatus());
            assertNotNull(historicoTurma.getDataFim());
            verify(repository).save(aluno);
        }

        @Test
        @DisplayName("Lança exceção quando aluno não possui histórico ativo")
        void shouldThrowExceptionWhenHistoricoNotActive() {
            aluno.setHistoricoTurmas(new ArrayList<>()); // Nenhum histórico ativo
            given(repository.findById(1L)).willReturn(Optional.of(aluno));

            var exception = assertThrows(HistoricoNotFoundException.class, () -> service.disableAluno(1L));

            assertEquals("Historico de turmas do aluno com Id 1 não foi encontrada", exception.getMessage());
            verify(repository, never()).save(any(Aluno.class));
        }

        @Test
        @DisplayName("Lança exceção quando aluno já está inativo")
        void shouldNotDisableAlunoAlreadyInactive() {
            aluno.setStatus(Status.INATIVO);
            given(repository.findById(1L)).willReturn(Optional.of(aluno));

            service.disableAluno(1L);

            assertEquals(Status.INATIVO, aluno.getStatus()); // Status permanece inativo
            assertNull(historicoTurma.getDataFim()); // Nenhuma alteração no histórico
            verify(repository, never()).save(any(Aluno.class));
        }

        @Test
        @DisplayName("Lança exceção quando aluno não encontrado")
        void shouldThrowExceptionWhenAlunoNotFound() {
            given(repository.findById(1L)).willReturn(Optional.empty());

            var exception = assertThrows(AlunoNotFoundException.class, () -> service.disableAluno(1L));

            assertEquals("Aluno de Id 1 não foi encontrado", exception.getMessage());
            verify(repository, never()).save(any(Aluno.class));
        }
    }

    @Nested
    class TransferAluno {

        @Test
        @DisplayName("Transfere um aluno com sucesso")
        public void transferAlunoSuccessfully() {
            // Mock do aluno existente
            given(repository.findById(1L)).willReturn(Optional.of(aluno));

            // Execução do método
            service.transferAluno(1L);

            // Validações
            assertEquals(Status.TRANSFERIDO, aluno.getStatus());
            verify(repository).findById(1L);
        }

        @Test
        @DisplayName("Lança AlunoNotFoundException ao tentar transferir um aluno inexistente")
        public void transferAlunoThrowsAlunoNotFoundException() {
            // Mock de aluno inexistente
            given(repository.findById(1L)).willReturn(Optional.empty());

            // Execução do método e validação da exceção
            assertThrows(AlunoNotFoundException.class, () -> service.transferAluno(1L));

            // Verificar interações
            verify(repository).findById(1L);
            verifyNoMoreInteractions(repository);
        }
    }
}




