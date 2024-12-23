package com.api.diario.services.security;

import com.api.diario.core.security.TokenService;
import com.api.diario.domain.model.usuarios.ProfessorRole;
import com.api.diario.domain.model.usuarios.Usuario;
import com.api.diario.domain.model.usuarios.UsuarioRole;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TokenServiceTest {

    private TokenService tokenService;

    private Usuario usuario = new Usuario();

    @BeforeEach
    void setUp() {
        TokenService tokenService = new TokenService();
        ReflectionTestUtils.setField(tokenService, "secretKey", "test-secret-key");
        this.tokenService = tokenService;

        usuario.setName("teste");
        usuario.setEmail("test@email.com");

        UsuarioRole role = new ProfessorRole();
        role.setRole("PROFESSOR");
        role.setUsuario(usuario);
        usuario.setUsuarioRole(role);

    }

    @Nested
    public class generateToken{

        @Test
        @DisplayName("Gerando token com usuario valido")
        public void test_generate_token_with_valid_user() {

            String token = tokenService.generateToken(usuario);

            DecodedJWT decodedJWT = JWT.decode(token);

            assertNotNull(token);
            assertEquals("test@email.com", decodedJWT.getSubject());
            assertEquals("PROFESSOR", decodedJWT.getClaim("role").asList(String.class).get(0));
            assertEquals("Servico_de_login", decodedJWT.getIssuer());
        }

    }

    @Nested
    class validateToken{

        @Test
        @DisplayName("Ler token valido")
        public void readValidToken(){

            String token = tokenService.generateToken(usuario);

            DecodedJWT decodedJWT = tokenService.validateToken(token);

            assertNotNull(token);
            assertEquals("test@email.com", decodedJWT.getSubject());
            assertEquals("Servico_de_login", decodedJWT.getIssuer());
            assertEquals("PROFESSOR", decodedJWT.getClaim("role").asList(String.class).get(0));
        }
    }

}
