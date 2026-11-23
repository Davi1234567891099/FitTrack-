package com.senai.fittrack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "series_registro")
@Getter
@Setter
@NoArgsConstructor
public class SerieRegistro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Integer numeroSerie;
	
	@Column(nullable = false)
	private Integer repeticoes;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal carga;
	private Integer descanso;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "registro_treino_id")
	@JsonIgnore
	private RegistroTreino registroTreino;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "item_treino_id")
	private ItemTreino itemTreino;
}
