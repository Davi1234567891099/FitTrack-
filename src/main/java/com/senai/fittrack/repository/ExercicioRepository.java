package com.senai.fittrack.repository;

import com.senai.fittrack.model.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {
}
