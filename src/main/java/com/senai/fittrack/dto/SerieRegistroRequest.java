package com.senai.fittrack.dto;

import java.math.BigDecimal;

public record SerieRegistroRequest(Integer numeroSerie, Integer repeticoes, BigDecimal carga, Integer descanso,
		Long itemTreinoId) {
}
