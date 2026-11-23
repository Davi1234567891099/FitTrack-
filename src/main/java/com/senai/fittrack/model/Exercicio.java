package com.senai.fittrack.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exercicios")
@Getter
@Setter
@NoArgsConstructor
public class Exercicio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(length = 2000)
	private String descricao;
	private String grupoMuscular;
	private String videoUrl;
	
	@Column(nullable = false)
	private Boolean ativo = true;
}
