package com.api.diario.domain.services.security;

import com.api.diario.api.auth.dto.LoginDTO;
import com.api.diario.api.auth.dto.RegisterDTO;
import com.api.diario.api.auth.dto.ResponseDTO;

public interface AuthService {

    public ResponseDTO login(LoginDTO dto);

    public ResponseDTO register(RegisterDTO dto);
}
