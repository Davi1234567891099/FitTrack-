package com.senai.fittrack.repository;

import com.senai.fittrack.dto.EvolucaoExercicioResponse;
import com.senai.fittrack.model.SerieRegistro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SerieRegistroRepository extends JpaRepository<SerieRegistro, Long> {
    @Query("""
            select new com.senai.fittrack.dto.EvolucaoExercicioResponse(
                r.data, e.nome, max(s.carga), count(s.id))
            from SerieRegistro s
            join s.registroTreino r
            join s.itemTreino i
            join i.exercicio e
            where r.usuario.id = :usuarioId
              and (:exercicioId is null or e.id = :exercicioId)
            group by r.data, e.nome
            order by r.data, e.nome
            """)
    List<EvolucaoExercicioResponse> buscarEvolucao(
            @Param("usuarioId") Long usuarioId,
            @Param("exercicioId") Long exercicioId);
}
