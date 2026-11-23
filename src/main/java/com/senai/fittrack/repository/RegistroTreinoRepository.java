package com.senai.fittrack.repository;

import com.senai.fittrack.model.RegistroTreino;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegistroTreinoRepository extends JpaRepository<RegistroTreino, Long> {
	List<RegistroTreino> findByUsuarioIdOrderByDataDesc(Long usuarioId);
}
