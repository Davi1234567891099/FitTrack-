package com.senai.fittrack.controller;

import com.senai.fittrack.dto.UsuarioRequest;
import com.senai.fittrack.model.Usuario;
import com.senai.fittrack.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
	
	private final UsuarioService service;

	@GetMapping
	List<Usuario> listar() {
		return service.listar();
	}

	@GetMapping("/{id}")
	Usuario buscar(@PathVariable Long id) {
		return service.buscar(id);
	}

	@PutMapping("/{id}")
	Usuario atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequest r) {
		return service.atualizar(id, r);
	}

	@DeleteMapping("/{id}")
	ResponseEntity<Void> desativar(@PathVariable Long id) {
		service.desativar(id);
		return ResponseEntity.noContent().build();
	}
}
