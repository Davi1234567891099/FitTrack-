package com.senai.fittrack.controller;

import com.senai.fittrack.dto.EvolucaoExercicioResponse;
import com.senai.fittrack.service.EvolucaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/evolucao")
@RequiredArgsConstructor
public class EvolucaoController {
    private final EvolucaoService service;

    @GetMapping
    public List<EvolucaoExercicioResponse> consultar(
            @RequestParam Long usuarioId,
            @RequestParam(required = false) Long exercicioId) {
        return service.consultar(usuarioId, exercicioId);
    }
}
