package com.senai.fittrack.controller;

import com.senai.fittrack.dto.*;
import com.senai.fittrack.model.*;
import com.senai.fittrack.service.RegistroTreinoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/registros-treino")
@RequiredArgsConstructor
public class RegistroTreinoController {
	
	private final RegistroTreinoService service;

	@GetMapping
	List<RegistroTreino> listar(@RequestParam(required = false) Long usuarioId) {
		return service.listar(usuarioId);
	}

	@GetMapping("/{id}")
	RegistroTreino buscar(@PathVariable Long id) {
		return service.buscar(id);
	}

	@PostMapping
	ResponseEntity<RegistroTreino> criar(@RequestBody RegistroTreinoRequest r) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(r));
	}

	@PostMapping("/{id}/series")
	ResponseEntity<SerieRegistro> adicionarSerie(@PathVariable Long id, @RequestBody SerieRegistroRequest r) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.adicionarSerie(id, r));
	}

	@DeleteMapping("/{id}")
	ResponseEntity<Void> excluir(@PathVariable Long id) {
		service.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
