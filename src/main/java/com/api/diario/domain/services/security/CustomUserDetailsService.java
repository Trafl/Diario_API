package com.api.diario.domain.services.security;

import com.api.diario.domain.model.usuarios.CustomUserDetails;
import com.api.diario.domain.model.usuarios.Usuario;
import com.api.diario.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        var role = usuario.getUsuarioRole().getRole();

        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));

        return new CustomUserDetails(usuario.getId(), usuario.getEmail(), usuario.getPassword(),authorities);

    }
}
