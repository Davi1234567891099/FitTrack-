package com.senai.fittrack.dto;

import jakarta.validation.constraints.*;

public record LoginRequest(@NotBlank @Email String email, @NotBlank String senha) {
}
