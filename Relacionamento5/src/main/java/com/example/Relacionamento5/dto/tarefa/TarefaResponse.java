package com.example.Relacionamento5.dto.tarefa;

public record TarefaResponse(
        Long id,
        String titulo,
        String status,
        Long projetoId,
        String projetoNome
) {
}
