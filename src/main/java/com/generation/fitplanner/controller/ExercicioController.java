package com.generation.fitplanner.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.fitplanner.model.Exercicio;
import com.generation.fitplanner.repository.ExercicioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/exercicios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExercicioController {

	@Autowired
	private ExercicioRepository exercicioRepository;

	@GetMapping
	public ResponseEntity<Page<Exercicio>> getAll(@PageableDefault Pageable pageable) {
		Page<Exercicio> exercicioPage = exercicioRepository.findAll(pageable);
		return ResponseEntity.ok(exercicioPage);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Exercicio> getById(@PathVariable Long id) {
		return exercicioRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/nomeExercicio/{nomeExercicio}")
	public ResponseEntity<Page<Exercicio>> getByTitulo(@PathVariable String nomeExercicio, @PageableDefault Pageable pageable) {
		return ResponseEntity.ok(exercicioRepository.findAllByNomeExercicioContainingIgnoreCase(nomeExercicio, pageable));
	}

	@PostMapping
	public ResponseEntity<Exercicio> post(@Valid @RequestBody Exercicio exercicio) {
		return ResponseEntity.status(HttpStatus.CREATED).body(exercicioRepository.save(exercicio));
	}

	@PutMapping
	public ResponseEntity<Exercicio> put(@Valid @RequestBody Exercicio exercicio) {
		return exercicioRepository.findById(exercicio.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(exercicioRepository.save(exercicio)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Exercicio> exercicio = exercicioRepository.findById(id);

		if (exercicio.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		exercicioRepository.deleteById(id);
	}

}
