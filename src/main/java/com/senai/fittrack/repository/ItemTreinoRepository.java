package com.senai.fittrack.repository;

import com.senai.fittrack.model.ItemTreino;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemTreinoRepository extends JpaRepository<ItemTreino, Long> {
	List<ItemTreino> findByTreinoIdOrderByOrdem(Long treinoId);
}
