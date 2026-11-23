package com.senai.fittrack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "itens_treino")
@Getter
@Setter
@NoArgsConstructor
public class ItemTreino {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Integer ordem;
	private Integer seriesPadrao;
	private Integer repeticoesPadrao;
	
	@Column(precision = 10, scale = 2)
	private BigDecimal cargaPadrao;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "treino_id")
	@JsonIgnore
	private Treino treino;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "exercicio_id")
	private Exercicio exercicio;
}
