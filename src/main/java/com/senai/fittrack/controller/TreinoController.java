package com.senai.fittrack.controller;

import com.senai.fittrack.dto.*;
import com.senai.fittrack.model.*;
import com.senai.fittrack.service.TreinoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/treinos")
@RequiredArgsConstructor
public class TreinoController {
	
	private final TreinoService service;

	@GetMapping
	List<Treino> listar(@RequestParam(required = false) Long usuarioId) {
		return service.listar(usuarioId);
	}

	@GetMapping("/{id}")
	Treino buscar(@PathVariable Long id) {
		return service.buscar(id);
	}

	@PostMapping
	ResponseEntity<Treino> criar(@Valid @RequestBody TreinoRequest r) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(r));
	}

	@PutMapping("/{id}")
	Treino atualizar(@PathVariable Long id, @Valid @RequestBody TreinoRequest r) {
		return service.atualizar(id, r);
	}

	@DeleteMapping("/{id}")
	ResponseEntity<Void> desativar(@PathVariable Long id) {
		service.desativar(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}/itens")
	List<ItemTreino> listarItens(@PathVariable Long id) {
		return service.listarItens(id);
	}

	@PostMapping("/{id}/itens")
	ResponseEntity<ItemTreino> adicionarItem(@PathVariable Long id, @RequestBody ItemTreinoRequest r) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.adicionarItem(id, r));
	}

	@DeleteMapping("/{treinoId}/itens/{itemId}")
	ResponseEntity<Void> removerItem(@PathVariable Long treinoId, @PathVariable Long itemId) {
		service.removerItem(treinoId, itemId);
		return ResponseEntity.noContent().build();
	}
}
