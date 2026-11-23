package com.senai.fittrack.dto;

import jakarta.validation.constraints.*;

public record UsuarioRequest(@NotBlank String nome, @NotBlank @Email String email,
		@NotBlank @Size(min = 6) String senha) {
}
