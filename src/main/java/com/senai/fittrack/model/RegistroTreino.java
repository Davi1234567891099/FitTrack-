package com.senai.fittrack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "registros_treino")
@Getter
@Setter
@NoArgsConstructor
public class RegistroTreino {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDate data;
	
	@Column(length = 2000)
	private String observacoes;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "usuario_id")
	@JsonIgnoreProperties({ "treinos", "registros", "senha" })
	private Usuario usuario;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "treino_id")
	@JsonIgnoreProperties("usuario")
	private Treino treino;
	
	@OneToMany(mappedBy = "registroTreino", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SerieRegistro> series = new ArrayList<>();

	@PrePersist
	void prePersist() {
		if (data == null)
			data = LocalDate.now();
	}
}
