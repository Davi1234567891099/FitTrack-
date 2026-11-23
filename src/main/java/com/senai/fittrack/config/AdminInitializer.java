package com.senai.fittrack.config;

import com.senai.fittrack.model.*;
import com.senai.fittrack.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {
    private static final Logger log = LoggerFactory.getLogger(AdminInitializer.class);

    private final UsuarioRepository usuarios;
    private final PasswordEncoder passwordEncoder;
    private final ExercicioRepository exercicios;
    private final TreinoRepository treinos;
    private final ItemTreinoRepository itensTreino;
    private final RegistroTreinoRepository registros;
    private final SerieRegistroRepository series;

    @Bean
    CommandLineRunner criarAdministradorPadrao(
            @Value("${fittrack.admin.email:admin@fittrack.com}") String email,
            @Value("${fittrack.admin.password:admin123}") String senha) {
        return args -> {
            Usuario admin = usuarios.findByEmail(email).orElse(null);
            if (admin == null) {
                admin = new Usuario();
                admin.setNome("Administrador");
                admin.setEmail(email);
                admin.setSenha(passwordEncoder.encode(senha));
                admin.setRole(Role.ADMINISTRADOR);
                admin.setAtivo(true);
                admin = usuarios.save(admin);
                log.info("Administrador padrão criado: {}", email);
            }
            criarTreinoDemonstracao(admin);
        };
    }

    private void criarTreinoDemonstracao(Usuario admin) {
        String nomeTreino = "Treino A - Demonstração";
        if (treinos.existsByUsuarioIdAndNome(admin.getId(), nomeTreino)) return;

        Exercicio supino = obterExercicio("Supino reto", "Peitoral", "Exercício para peitoral com barra.");
        Exercicio agachamento = obterExercicio("Agachamento livre", "Pernas", "Exercício composto para pernas.");

        Treino treino = new Treino();
        treino.setNome(nomeTreino);
        treino.setDescricao("Treino criado automaticamente para demonstrar o painel de evolução.");
        treino.setUsuario(admin);
        treino = treinos.save(treino);

        ItemTreino itemSupino = criarItem(treino, supino, 1, 3, 10, "40.00");
        criarItem(treino, agachamento, 2, 3, 12, "50.00");
        criarRegistroComSerie(admin, treino, itemSupino, LocalDate.now().minusDays(12), "Primeiro treino registrado.", "40.00");
        criarRegistroComSerie(admin, treino, itemSupino, LocalDate.now().minusDays(7), "Aumento progressivo de carga.", "45.00");
        criarRegistroComSerie(admin, treino, itemSupino, LocalDate.now().minusDays(2), "Boa evolução no supino.", "50.00");
        log.info("Treino de demonstração criado para o administrador padrão.");
    }

    private Exercicio obterExercicio(String nome, String grupoMuscular, String descricao) {
        return exercicios.findByNome(nome).orElseGet(() -> {
            Exercicio exercicio = new Exercicio();
            exercicio.setNome(nome);
            exercicio.setGrupoMuscular(grupoMuscular);
            exercicio.setDescricao(descricao);
            exercicio.setAtivo(true);
            return exercicios.save(exercicio);
        });
    }

    private ItemTreino criarItem(Treino treino, Exercicio exercicio, int ordem, int seriesPadrao, int repeticoesPadrao, String cargaPadrao) {
        ItemTreino item = new ItemTreino();
        item.setTreino(treino);
        item.setExercicio(exercicio);
        item.setOrdem(ordem);
        item.setSeriesPadrao(seriesPadrao);
        item.setRepeticoesPadrao(repeticoesPadrao);
        item.setCargaPadrao(new BigDecimal(cargaPadrao));
        return itensTreino.save(item);
    }

    private void criarRegistroComSerie(Usuario usuario, Treino treino, ItemTreino item, LocalDate data, String observacoes, String carga) {
        RegistroTreino registro = new RegistroTreino();
        registro.setUsuario(usuario);
        registro.setTreino(treino);
        registro.setData(data);
        registro.setObservacoes(observacoes);
        registro = registros.save(registro);
        SerieRegistro serie = new SerieRegistro();
        serie.setRegistroTreino(registro);
        serie.setItemTreino(item);
        serie.setNumeroSerie(1);
        serie.setRepeticoes(10);
        serie.setCarga(new BigDecimal(carga));
        serie.setDescanso(90);
        series.save(serie);
    }
}
