package com.api.diario.core.security;

import com.api.diario.domain.exception.login.UserNotFoundException;
import com.api.diario.domain.model.usuarios.CustomUserDetails;
import com.api.diario.domain.model.usuarios.Usuario;
import com.api.diario.domain.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
@EnableMethodSecurity()
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final UsuarioRepository repository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var token = this.recoverToken(request);

        if (token != null) {
            try {
                var decodedJWT = tokenService.validateToken(token);

                if (decodedJWT != null) {
                    var email = decodedJWT.getSubject();
                    List<String> roles = decodedJWT.getClaim("role").asList(String.class);

                    Usuario usuario = repository.findByEmail(email)
                            .orElseThrow(() -> new UserNotFoundException(email));

                    var authorities = roles.stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())).toList();

                    var customUserDetails = new CustomUserDetails(usuario.getId(), usuario.getEmail(), usuario.getPassword(), authorities);

                    var authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (IllegalArgumentException e) {
                log.warn("Token inválido ou ausente: {}", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
