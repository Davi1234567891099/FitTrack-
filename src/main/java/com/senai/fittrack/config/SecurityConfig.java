package com.senai.fittrack.config;

import com.senai.fittrack.model.Usuario;
import com.senai.fittrack.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final UsuarioRepository usuarios;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserDetailsService userDetailsService() {
		return email -> {
			Usuario u = usuarios.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
			return new User(u.getEmail(), u.getSenha(), u.getAtivo(), true, true, true,
					java.util.List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole().name())));
		};
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/index.html", "/css/**", "/js/**", "/api/auth/**").permitAll()
						.anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults()).build();
	}
}
