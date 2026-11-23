package com.senai.fittrack.repository;

import com.senai.fittrack.model.Treino;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TreinoRepository extends JpaRepository<Treino, Long> {
	List<Treino> findByUsuarioId(Long usuarioId);
	boolean existsByUsuarioIdAndNome(Long usuarioId, String nome);
}
