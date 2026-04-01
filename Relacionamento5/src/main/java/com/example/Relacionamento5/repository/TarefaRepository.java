package com.example.Relacionamento5.repository;

import com.example.Relacionamento5.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByProjetoId(Long projetoId);

    Optional<Tarefa> findByIdAndTituloContainingIgnoreCase(Long id, String titulo);
}