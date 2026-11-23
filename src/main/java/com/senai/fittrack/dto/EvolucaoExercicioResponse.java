package com.senai.fittrack.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EvolucaoExercicioResponse(
        LocalDate data,
        String exercicio,
        BigDecimal cargaMaxima,
        Long totalSeries
) { }
