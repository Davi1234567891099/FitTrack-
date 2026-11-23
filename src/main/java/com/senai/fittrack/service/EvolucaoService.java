package com.senai.fittrack.service;

import com.senai.fittrack.dto.EvolucaoExercicioResponse;
import com.senai.fittrack.exception.ResourceNotFoundException;
import com.senai.fittrack.repository.SerieRegistroRepository;
import com.senai.fittrack.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvolucaoService {
    private final SerieRegistroRepository series;
    private final UsuarioRepository usuarios;

    public List<EvolucaoExercicioResponse> consultar(Long usuarioId, Long exercicioId) {
        if (!usuarios.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuário", usuarioId);
        }
        return series.buscarEvolucao(usuarioId, exercicioId);
    }
}
