package com.api.diario.api.globalexception;

import com.api.diario.domain.exception.aluno.AlunoNotFoundException;
import com.api.diario.domain.exception.aluno.DataExistingException;
import com.api.diario.domain.exception.diario.DiarioNotFoundException;
import com.api.diario.domain.exception.frequencia.FrequenciaNotFoundException;
import com.api.diario.domain.exception.historicoturma.HistoricoNotFoundException;
import com.api.diario.domain.exception.login.ExistUserInDbException;
import com.api.diario.domain.exception.login.IncorrectPasswordException;
import com.api.diario.domain.exception.login.InvalidRoleException;
import com.api.diario.domain.exception.login.UserNotFoundException;
import com.api.diario.domain.exception.trimestre.TrimestreNotFoundException;
import com.api.diario.domain.exception.turma.TurmaNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    private String timestamp = LocalDateTime.now().toString();

    //-------------LOGIN---------------------------------

    @ExceptionHandler(UserNotFoundException.class)
    ProblemDetail handlerUserNotFoundException(UserNotFoundException e) {

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

        log.warn("[{}] - GlobalException: {}", timestamp, e.getMessage());

        problem.setTitle("Usuario não encontrado");
        problem.setProperty("timestamp", Instant.now());

        return problem;

    }

    @ExceptionHandler(IncorrectPasswordException.class)
    ProblemDetail handlerIncorrectPasswordException(IncorrectPasswordException e) {

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());

        log.warn("[{}] - GlobalException: {}", timestamp, e.getMessage());

        problem.setTitle("Senha incorreta");
        problem.setProperty("timestamp", Instant.now());

        return problem;

    }

    @ExceptionHandler(InvalidRoleException.class)
    ProblemDetail handlerInvalidRoleException(InvalidRoleException e) {

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());

        log.error("[{}] - GlobalException: {}", timestamp, e.getMessage());

        problem.setTitle("Role não reconhecida");
        problem.setProperty("timestamp", Instant.now());

        return problem;

    }

    @ExceptionHandler(ExistUserInDbException.class)
    ProblemDetail handlerExistUserInDbException(ExistUserInDbException e) {

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());

        log.warn("[{}] - GlobalException: {}", timestamp, e.getMessage());

        problem.setTitle("Já existe um usuario com esse email");
        problem.setProperty("timestamp", Instant.now());

        return problem;

    }

    //-------------ALUNO---------------------------------

    @ExceptionHandler(DataExistingException.class)
    ProblemDetail handlerDataExistingException(DataExistingException e) {

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());

        log.warn("[{}] - GlobalException: {}", timestamp, e.getMessage());

        problem.setTitle("Já existe um Aluno com essas informações");
        problem.setProperty("timestamp", Instant.now());

        return problem;

    }

    @ExceptionHandler(AlunoNotFoundException.class)
    ProblemDetail handlerAlunoNotFoundException(AlunoNotFoundException e) {

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

        log.warn("[{}] - GlobalException: {}", timestamp, e.getMessage());

        problem.setTitle("Aluno não foi encontrado");
        problem.setProperty("timestamp", Instant.now());

        return problem;

    }

    //-------------Turma---------------------------------

    @ExceptionHandler(TurmaNotFoundException.class)
    ProblemDetail handlerTurmaNotFoundException(TurmaNotFoundException e) {

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

        log.warn("[{}] - GlobalException: {}", timestamp, e.getMessage());

        problem.setTitle("Turma não foi encontrado");
        problem.setProperty("timestamp", Instant.now());

        return problem;

    }


    //-------------Trimestre---------------------------------

    @ExceptionHandler(TrimestreNotFoundException.class)
    ProblemDetail handlerTrimestreNotFoundException(TrimestreNotFoundException e) {

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

        log.warn("[{}] - GlobalException: {}", timestamp, e.getMessage());

        problem.setTitle("Trimestre não foi encontrado");
        problem.setProperty("timestamp", Instant.now());

        return problem;

    }

    //-------------HistoricoDeTurmas---------------------------------

    @ExceptionHandler(HistoricoNotFoundException.class)
    ProblemDetail handlerHistoricoNotFoundException(HistoricoNotFoundException e) {

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

        log.warn("[{}] - GlobalException: {}", timestamp, e.getMessage());

        problem.setTitle("Historico de turmas não foi encontrado");
        problem.setProperty("timestamp", Instant.now());

        return problem;

    }

    //-------------Diario---------------------------------

    @ExceptionHandler(DiarioNotFoundException.class)
    ProblemDetail handlerDiarioNotFoundException(DiarioNotFoundException e) {

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

        log.warn("[{}] - GlobalException: {}", timestamp, e.getMessage());

        problem.setTitle("Diario não foi encontrado");
        problem.setProperty("timestamp", Instant.now());

        return problem;

    }

    //-------------Frequencia---------------------------------

    @ExceptionHandler(FrequenciaNotFoundException.class)
    ProblemDetail handlerFrequenciaNotFoundException(FrequenciaNotFoundException e) {

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

        log.warn("[{}] - GlobalException: {}", timestamp, e.getMessage());

        problem.setTitle("Frequencia não foi encontrada");
        problem.setProperty("timestamp", Instant.now());

        return problem;
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatusCode.valueOf(400));
        problem.setTitle("Erro ao validar os campos informados");
        problem.setDetail("Um ou mais campos estão incorretos. Corrija e tente novamente.");
        problem.setProperty("timestamp", Instant.now());

        Map<String, String> errors = getErrorFields(ex);
        errors.forEach(problem::setProperty);

        log.error("[{}] - [GlobalException] - MethodArgumentNotValidException: Error validating the fields entered", timestamp);
        errors.forEach((fieldName, message) -> {
            log.error("[{}] - [GlobalException] - Invalid field: {} - Message: {}", timestamp, fieldName, message);
        });

        return new ResponseEntity<Object>(problem, status);
    }

    private Map<String, String> getErrorFields(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return errors;
    }
}
