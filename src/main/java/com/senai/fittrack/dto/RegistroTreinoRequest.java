package com.senai.fittrack.dto;

import java.time.LocalDate;

public record RegistroTreinoRequest(LocalDate data, String observacoes, Long usuarioId, Long treinoId) {
}
