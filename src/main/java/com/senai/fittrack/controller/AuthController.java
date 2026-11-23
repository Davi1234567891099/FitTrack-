package com.senai.fittrack.controller;

import com.senai.fittrack.dto.*;
import com.senai.fittrack.model.Usuario;
import com.senai.fittrack.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final UsuarioService usuarios;
	private final PasswordEncoder encoder;

	@PostMapping("/cadastro")
	ResponseEntity<Usuario> cadastrar(@Valid @RequestBody UsuarioRequest r) {
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarios.cadastrar(r));
	}

	@PostMapping("/login")
	Map<String, Object> login(@Valid @RequestBody LoginRequest r) {
		Usuario u = usuarios.porEmail(r.email());
		if (!u.getAtivo() || !encoder.matches(r.senha(), u.getSenha()))
			throw new IllegalArgumentException("Credenciais inválidas");
		return Map.of("mensagem", "Autenticação realizada", "usuarioId", u.getId(), "nome", u.getNome());
	}
}
