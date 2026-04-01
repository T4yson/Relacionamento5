package com.example.Relacionamento5.dto.projeto;

import jakarta.validation.constraints.NotBlank;

public record ProjetoRequest(
        @NotBlank(message = "Nome do projeto e obrigatorio")
        String nome,
        @NotBlank(message = "Descricao do projeto e obrigatoria")
        String descricao
) {
}