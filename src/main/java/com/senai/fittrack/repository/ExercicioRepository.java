package com.senai.fittrack.repository;

import com.senai.fittrack.model.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {
	Optional<Exercicio> findByNome(String nome);
}
