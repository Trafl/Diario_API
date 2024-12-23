package com.api.diario.services.security;

import com.api.diario.api.auth.dto.LoginDTO;
import com.api.diario.api.auth.dto.ResponseDTO;
import com.api.diario.core.security.TokenService;
import com.api.diario.domain.exception.login.IncorrectPasswordException;
import com.api.diario.domain.exception.login.UserNotFoundException;
import com.api.diario.domain.model.usuarios.ProfessorRole;
import com.api.diario.domain.model.usuarios.Usuario;
import com.api.diario.domain.model.usuarios.UsuarioRole;
import com.api.diario.domain.repository.UsuarioRepository;
import com.api.diario.domain.repository.UsuarioRoleRepository;
import com.api.diario.domain.services.security.AuthServiceIMPL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AuthServiceIMPLTest {

    @InjectMocks
    private AuthServiceIMPL authService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    private Usuario mockUser;

    @BeforeEach
    void setUp(){

        mockUser = new Usuario();
        mockUser.setEmail("test@test.com");
        mockUser.setPassword(passwordEncoder.encode("password123"));
        mockUser.setName("Test User");

        UsuarioRole mockRole = new ProfessorRole();
        mockRole.setRole("PROFESSOR");
        mockUser.setUsuarioRole(mockRole);
    }

    @Nested
    class login {

        @Test
        @DisplayName("Login com usuario valido")
        public void loginWithValidUser(){

            given(usuarioRepository.findByEmail("test@test.com")).willReturn(Optional.of(mockUser));
            given(passwordEncoder.matches("password123", mockUser.getPassword())).willReturn(true);
            given(tokenService.generateToken(mockUser)).willReturn("mocked-token");

            LoginDTO loginDTO = new LoginDTO("test@test.com", "password123");

            // Act
            ResponseDTO response = authService.login(loginDTO);

            // Assert
            assertNotNull(response);
            assertEquals("Test User", response.name());
            assertEquals("PROFESSOR", response.role());
            assertNotNull(response.token());

        }

        @Test
        @DisplayName("Email invalido - Dispara UserNotFoundException")
        public void loginWithInvalidEmailUser(){

            given(usuarioRepository.findByEmail("test@test.com")).willReturn(Optional.empty());

            var loginDTO = new LoginDTO("test@test.com", "password");

            var result = assertThrows(UserNotFoundException.class,
                    ()-> {
                        authService.login(loginDTO);
                    });

            assertEquals("Usuario com email test@test.com não foi encontrado", result.getMessage());
        }

        @Test
        @DisplayName("Senha incorreta - Dispara IncorrectPasswordException")
        public void loginWithIncorrectPasswordThrowIncorrectIncorrectPasswordException(){

            given(usuarioRepository.findByEmail("test@test.com")).willReturn(Optional.of(mockUser));
            given(passwordEncoder.matches("password", mockUser.getPassword())).willReturn(false);

            var loginDTO = new LoginDTO("test@test.com", "password");

            var result = assertThrows(IncorrectPasswordException.class,
                    ()-> {
                        authService.login(loginDTO);
                    });

            assertEquals("Senha informada esta incorreta", result.getMessage());
        }
    }
}
