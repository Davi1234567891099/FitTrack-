package com.senai.fittrack.service;

import com.senai.fittrack.dto.UsuarioRequest;
import com.senai.fittrack.exception.ResourceNotFoundException;
import com.senai.fittrack.model.Usuario;
import com.senai.fittrack.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
	private final UsuarioRepository repository;
	private final PasswordEncoder encoder;

	public List<Usuario> listar() {
		return repository.findAll();
	}

	public Usuario buscar(Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário", id));
	}

	public Usuario cadastrar(UsuarioRequest req) {
		if (repository.findByEmail(req.email()).isPresent())
			throw new IllegalArgumentException("E-mail já cadastrado");
		Usuario u = new Usuario();
		u.setNome(req.nome());
		u.setEmail(req.email());
		u.setSenha(encoder.encode(req.senha()));
		return repository.save(u);
	}

	public Usuario atualizar(Long id, UsuarioRequest req) {
		Usuario u = buscar(id);
		u.setNome(req.nome());
		u.setEmail(req.email());
		if (req.senha() != null && !req.senha().isBlank())
			u.setSenha(encoder.encode(req.senha()));
		return repository.save(u);
	}

	public void desativar(Long id) {
		Usuario u = buscar(id);
		u.setAtivo(false);
		repository.save(u);
	}

	public Usuario porEmail(String email) {
		return repository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas"));
	}
}
