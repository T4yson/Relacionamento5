package com.example.Relacionamento5.dto.tarefa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TarefaRequest(
        @NotBlank(message = "Titulo da tarefa e obrigatorio")
        String titulo,
        @NotBlank(message = "Status da tarefa e obrigatorio")
        String status,
        @NotNull(message = "Projeto e obrigatorio")
        Long projetoId
) {
}
