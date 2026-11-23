package com.senai.fittrack.dto;

import jakarta.validation.constraints.NotBlank;

public record TreinoRequest(@NotBlank String nome, String descricao, Long usuarioId) {
}
