package com.api.diario.api.auth.dto;

import jakarta.validation.constraints.NotEmpty;

public record RegisterDTO(@NotEmpty String name,@NotEmpty String email,@NotEmpty String password,@NotEmpty String role) {
}
