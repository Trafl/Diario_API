package com.api.diario.core.security;

import com.api.diario.domain.model.usuarios.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Log4j2
@Service
public class TokenService {
    private String timestamp = LocalDateTime.now().toString();

    @Value("${SECRET-KEY}")
    private String secretKey;

    public String generateToken(Usuario usuario){

        log.info("[{}] - [TokenService] Gerando token para Usuario: {}", timestamp, usuario.getName());

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            var role = usuario.getUsuarioRole().getRole();

            return JWT.create()
                    .withIssuer("Servico_de_login")
                    .withSubject(usuario.getEmail())
                    .withClaim("role", List.of(role))
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

        } catch (JWTCreationException e){
            throw new RuntimeException(String.format(
                    "Erro ao criar o token para usuario: {}", usuario.getName()
            ));
        }
    }

    public DecodedJWT validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            log.info("[{}] - [TokenService] Validando token", timestamp);

            return JWT.require(algorithm)
                    .withIssuer("Servico_de_login")
                    .build()
                    .verify(token);

        } catch (JWTVerificationException e){
            throw new RuntimeException("Erro ao validar Token: " + e.getMessage());
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(4)
                .atZone(ZoneId.of("America/Sao_Paulo"))
                .toInstant();
    }
}
