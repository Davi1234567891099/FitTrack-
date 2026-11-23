package com.senai.fittrack.config;

import com.senai.fittrack.model.Role;
import com.senai.fittrack.model.Usuario;
import com.senai.fittrack.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {
    private static final Logger log = LoggerFactory.getLogger(AdminInitializer.class);

    private final UsuarioRepository usuarios;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner criarAdministradorPadrao(
            @Value("${fittrack.admin.email:admin@fittrack.com}") String email,
            @Value("${fittrack.admin.password:admin123}") String senha) {
        return args -> {
            if (usuarios.findByEmail(email).isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNome("Administrador");
                admin.setEmail(email);
                admin.setSenha(passwordEncoder.encode(senha));
                admin.setRole(Role.ADMINISTRADOR);
                admin.setAtivo(true);
                usuarios.save(admin);
                log.info("Administrador padrão criado: {}", email);
            }
        };
    }
}
