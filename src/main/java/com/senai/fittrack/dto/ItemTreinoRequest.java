package com.senai.fittrack.dto;

import java.math.BigDecimal;

public record ItemTreinoRequest(Integer ordem, Integer seriesPadrao, Integer repeticoesPadrao, BigDecimal cargaPadrao,
		Long exercicioId) {
}
