package com.senai.fittrack.service;

import com.senai.fittrack.exception.ResourceNotFoundException;
import com.senai.fittrack.model.Exercicio;
import com.senai.fittrack.repository.ExercicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExercicioService {
	private final ExercicioRepository repository;

	public List<Exercicio> listar() {
		return repository.findAll();
	}

	public Exercicio buscar(Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Exercício", id));
	}

	public Exercicio salvar(Exercicio exercicio) {
		return repository.save(exercicio);
	}

	public Exercicio atualizar(Long id, Exercicio dados) {
		Exercicio e = buscar(id);
		e.setNome(dados.getNome());
		e.setDescricao(dados.getDescricao());
		e.setGrupoMuscular(dados.getGrupoMuscular());
		e.setVideoUrl(dados.getVideoUrl());
		e.setAtivo(dados.getAtivo());
		return repository.save(e);
	}

	public void desativar(Long id) {
		Exercicio e = buscar(id);
		e.setAtivo(false);
		repository.save(e);
	}
}
