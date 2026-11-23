package com.senai.fittrack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@JsonIgnore
	@Column(nullable = false)
	private String senha;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime dataCadastro;
	
	@Column(nullable = false)
	private Boolean ativo = true;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role = Role.USUARIO;
	
	@JsonIgnore
	@OneToMany(mappedBy = "usuario")
	private List<Treino> treinos = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "usuario")
	private List<RegistroTreino> registros = new ArrayList<>();

	@PrePersist
	void prePersist() {
		if (dataCadastro == null)
			dataCadastro = LocalDateTime.now();
	}
}
