package com.api.diario.api.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LoginDTO(@NotEmpty String email, @NotEmpty String password) {
}
