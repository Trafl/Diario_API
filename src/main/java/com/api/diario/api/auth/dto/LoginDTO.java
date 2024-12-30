package com.api.diario.api.auth.dto;

import jakarta.validation.constraints.NotEmpty;

public record LoginDTO(@NotEmpty String email, @NotEmpty String password) {
}
