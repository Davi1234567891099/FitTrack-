package com.senai.fittrack.service;

import com.senai.fittrack.dto.*;
import com.senai.fittrack.exception.ResourceNotFoundException;
import com.senai.fittrack.model.*;
import com.senai.fittrack.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TreinoService {
	private final TreinoRepository treinos;
	private final UsuarioRepository usuarios;
	private final ItemTreinoRepository itens;
	private final ExercicioRepository exercicios;

	public List<Treino> listar(Long usuarioId) {
		return usuarioId == null ? treinos.findAll() : treinos.findByUsuarioId(usuarioId);
	}

	public Treino buscar(Long id) {
		return treinos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Treino", id));
	}

	public Treino criar(TreinoRequest r) {
		Treino t = new Treino();
		preencher(t, r, true);
		return treinos.save(t);
	}

	public Treino atualizar(Long id, TreinoRequest r) {
		Treino t = buscar(id);
		preencher(t, r, r.usuarioId() != null);
		return treinos.save(t);
	}

	private void preencher(Treino t, TreinoRequest r, boolean defineUsuario) {
		t.setNome(r.nome());
		t.setDescricao(r.descricao());
		if (defineUsuario) {
			if (r.usuarioId() == null)
				throw new IllegalArgumentException("usuarioId é obrigatório");
			
			t.setUsuario(usuarios.findById(r.usuarioId())
					.orElseThrow(() -> new ResourceNotFoundException("Usuário", r.usuarioId())));
		}
	}

	public ItemTreino adicionarItem(Long treinoId, ItemTreinoRequest r) {
		Treino t = buscar(treinoId);
		if (r.exercicioId() == null)
			throw new IllegalArgumentException("exercicioId é obrigatório");
		
		ItemTreino i = new ItemTreino();
		i.setTreino(t);
		i.setOrdem(r.ordem());
		i.setSeriesPadrao(r.seriesPadrao());
		i.setRepeticoesPadrao(r.repeticoesPadrao());
		i.setCargaPadrao(r.cargaPadrao());
		i.setExercicio(exercicios.findById(r.exercicioId())
				.orElseThrow(() -> new ResourceNotFoundException("Exercício", r.exercicioId())));
		
		return itens.save(i);
	}

	public List<ItemTreino> listarItens(Long treinoId) {
		buscar(treinoId);
		return itens.findByTreinoIdOrderByOrdem(treinoId);
	}

	public void removerItem(Long treinoId, Long itemId) {
		ItemTreino i = itens.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Item de treino", itemId));
		if (!i.getTreino().getId().equals(treinoId))
			throw new IllegalArgumentException("Item não pertence ao treino informado");
		itens.delete(i);
	}

	public void desativar(Long id) {
		Treino t = buscar(id);
		t.setAtivo(false);
		treinos.save(t);
	}
}
