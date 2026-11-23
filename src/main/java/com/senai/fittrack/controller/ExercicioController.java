package com.senai.fittrack.controller;

import com.senai.fittrack.model.Exercicio;
import com.senai.fittrack.service.ExercicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/exercicios")
@RequiredArgsConstructor
public class ExercicioController {
	
	private final ExercicioService service;

	@GetMapping
	List<Exercicio> listar() {
		return service.listar();
	}

	@GetMapping("/{id}")
	Exercicio buscar(@PathVariable Long id) {
		return service.buscar(id);
	}

	@PostMapping
	ResponseEntity<Exercicio> criar(@RequestBody Exercicio e) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(e));
	}

	@PutMapping("/{id}")
	Exercicio atualizar(@PathVariable Long id, @RequestBody Exercicio e) {
		return service.atualizar(id, e);
	}

	@DeleteMapping("/{id}")
	ResponseEntity<Void> desativar(@PathVariable Long id) {
		service.desativar(id);
		return ResponseEntity.noContent().build();
	}
}
