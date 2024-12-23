package com.api.diario.domain.services.security;

import com.api.diario.api.auth.dto.LoginDTO;
import com.api.diario.api.auth.dto.RegisterDTO;
import com.api.diario.api.auth.dto.ResponseDTO;
import com.api.diario.core.security.TokenService;
import com.api.diario.domain.exception.login.ExistUserInDbException;
import com.api.diario.domain.exception.login.IncorrectPasswordException;
import com.api.diario.domain.exception.login.InvalidRoleException;
import com.api.diario.domain.exception.login.UserNotFoundException;
import com.api.diario.domain.model.usuarios.*;
import com.api.diario.domain.repository.UsuarioRepository;
import com.api.diario.domain.repository.UsuarioRoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceIMPL implements AuthService{

    private final UsuarioRepository usuarioRepository;

    private final UsuarioRoleRepository usuarioRoleRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    String timestamp = LocalDateTime.now().toString();

    @Override
    public ResponseDTO login(LoginDTO dto) {

        log.info("[{}] - [AuthServiceIMPL] - Requisição de login com email: {}", timestamp, dto.email());

        var user = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(()-> new UserNotFoundException(dto.email()));

        var role = user.getUsuarioRole().getRole();

        if(passwordEncoder.matches(dto.password(), user.getPassword())){
            log.info("[{}] - [AuthServiceIMPL] - Senha verificada com sucesso para email: {}", timestamp, dto.email());

            String token = tokenService.generateToken(user);
            return new ResponseDTO(user.getName(),role,token);
        }
        else{
            throw new IncorrectPasswordException();
        }
    }

    @Override
    @Transactional
    public ResponseDTO register(RegisterDTO dto) {

        log.info("[{}] - [AuthServiceIMPL] - Requisição de registro com email: {}", timestamp, dto.email());

        var userInDB = usuarioRepository.findByEmail(dto.email());

        if (userInDB.isEmpty()) {
            Usuario newUsuario = new Usuario();
            newUsuario.setName(dto.name());
            newUsuario.setEmail(dto.email());
            newUsuario.setPassword(passwordEncoder.encode(dto.password()));

            UsuarioRole usuarioRole;

            if (dto.role().equalsIgnoreCase("PROFESSOR")) {
                usuarioRole = new ProfessorRole();
                ((ProfessorRole) usuarioRole).setEspecialidade("Matematica");

            } else if (dto.role().equalsIgnoreCase("DIRETOR")) {
                usuarioRole = new DiretorRole();
                ((DiretorRole) usuarioRole).setDepartamento("Diretor");

            }else if (dto.role().equalsIgnoreCase("SECRETARIA")) {
                usuarioRole = new SecretariaRole();
                ((SecretariaRole) usuarioRole).setDepartamento("secretaria");

            } else {
                throw new InvalidRoleException("The provided role '" + dto.role() + "' is not recognized.");
            }

            usuarioRole.setRole(dto.role().toUpperCase());
            usuarioRole.setUsuario(newUsuario);
            newUsuario.setUsuarioRole(usuarioRole);

            newUsuario = usuarioRepository.save(newUsuario);
            usuarioRoleRepository.save(usuarioRole);

            String token = tokenService.generateToken(newUsuario);

            return new ResponseDTO(newUsuario.getName(), newUsuario.getUsuarioRole().getRole(), token);
        } else {
            throw new ExistUserInDbException(dto.email());
        }
    }
}
