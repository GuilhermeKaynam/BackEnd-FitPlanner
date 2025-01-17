package com.generation.fitplanner.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.fitplanner.model.Exercicio;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long>{
	
	public Page<Exercicio> findAllByNomeExercicioContainingIgnoreCase(@Param("nomeExercicio")String nomeExercicio, Pageable pageable);

}
