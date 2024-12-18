package com.api.diario.api.auth.controller;

import com.api.diario.api.auth.dto.LoginDTO;
import com.api.diario.api.auth.dto.RegisterDTO;
import com.api.diario.api.auth.dto.ResponseDTO;
import com.api.diario.domain.services.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO dto){
        var response = service.login(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterDTO dto){
        var response = service.register(dto);
        return ResponseEntity.ok(response);
    }
}
