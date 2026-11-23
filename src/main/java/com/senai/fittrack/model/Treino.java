package com.senai.fittrack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "treinos")
@Getter
@Setter
@NoArgsConstructor
public class Treino {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(length = 2000)
	private String descricao;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime dataCriacao;
	
	@Column(nullable = false)
	private Boolean ativo = true;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "usuario_id")
	@JsonIgnoreProperties({ "treinos", "registros", "senha" })
	private Usuario usuario;
	
	@OneToMany(mappedBy = "treino", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemTreino> itens = new ArrayList<>();

	@PrePersist
	void prePersist() {
		if (dataCriacao == null)
			dataCriacao = LocalDateTime.now();
	}
}
