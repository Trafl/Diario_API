package com.api.diario.domain.services.security;

import com.api.diario.api.auth.dto.LoginDTO;
import com.api.diario.api.auth.dto.RegisterDTO;
import com.api.diario.api.auth.dto.ResponseDTO;
import com.api.diario.core.security.TokenService;
import com.api.diario.domain.exception.login.ExistUserInDbException;
import com.api.diario.domain.exception.login.IncorrectPasswordException;
import com.api.diario.domain.exception.login.UserNotFoundException;
import com.api.diario.domain.model.Usuario;
import com.api.diario.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceIMPL implements AuthService{

    private final UsuarioRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;
    @Override
    public ResponseDTO login(LoginDTO dto) {

        var user = repository.findByEmail(dto.email())
                .orElseThrow(()-> new UserNotFoundException("User not Found"));

        if(passwordEncoder.matches(dto.password(), user.getPassword())){
            String token = tokenService.generateToken(user);
            return new ResponseDTO(user.getName(),token);
        }
        else{
            throw new IncorrectPasswordException("incorrect password");
        }
    }

    @Override
    public ResponseDTO register(RegisterDTO dto) {
        var user = repository.findByEmail(dto.email());

        if(user.isEmpty()){
            Usuario newUsuario = new Usuario();
            newUsuario.setName(dto.name());
            newUsuario.setEmail(dto.email());
            newUsuario.setPassword(passwordEncoder.encode(dto.password()));

            repository.save(newUsuario);

            String token = tokenService.generateToken(newUsuario);
            return new ResponseDTO(newUsuario.getName(),token);
        }
        else {
            throw new ExistUserInDbException("User exist try another email");
        }
    }
}
