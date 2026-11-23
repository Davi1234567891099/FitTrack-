package com.senai.fittrack.service;

import com.senai.fittrack.dto.*;
import com.senai.fittrack.exception.ResourceNotFoundException;
import com.senai.fittrack.model.*;
import com.senai.fittrack.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistroTreinoService {
	private final RegistroTreinoRepository registros;
	private final UsuarioRepository usuarios;
	private final TreinoRepository treinos;
	private final SerieRegistroRepository series;
	private final ItemTreinoRepository itens;

	public List<RegistroTreino> listar(Long usuarioId) {
		return usuarioId == null ? registros.findAll() : registros.findByUsuarioIdOrderByDataDesc(usuarioId);
	}

	public RegistroTreino buscar(Long id) {
		return registros.findById(id).orElseThrow(() -> new ResourceNotFoundException("Registro de treino", id));
	}

	public RegistroTreino criar(RegistroTreinoRequest r) {
		if (r.usuarioId() == null || r.treinoId() == null)
			throw new IllegalArgumentException("usuarioId e treinoId são obrigatórios");
		
		RegistroTreino x = new RegistroTreino();
		x.setData(r.data() == null ? LocalDate.now() : r.data());
		x.setObservacoes(r.observacoes());
		x.setUsuario(usuarios.findById(r.usuarioId())
				.orElseThrow(() -> new ResourceNotFoundException("Usuário", r.usuarioId())));
		x.setTreino(treinos.findById(r.treinoId())
				.orElseThrow(() -> new ResourceNotFoundException("Treino", r.treinoId())));
		return registros.save(x);
	}

	public SerieRegistro adicionarSerie(Long registroId, SerieRegistroRequest r) {
		RegistroTreino reg = buscar(registroId);
		if (r.itemTreinoId() == null)
			throw new IllegalArgumentException("itemTreinoId é obrigatório");
		SerieRegistro s = new SerieRegistro();
		s.setRegistroTreino(reg);
		s.setNumeroSerie(r.numeroSerie());
		s.setRepeticoes(r.repeticoes());
		s.setCarga(r.carga());
		s.setDescanso(r.descanso());
		s.setItemTreino(itens.findById(r.itemTreinoId())
				.orElseThrow(() -> new ResourceNotFoundException("Item de treino", r.itemTreinoId())));
		
		return series.save(s);
	}

	public void excluir(Long id) {
		registros.delete(buscar(id));
	}
}
