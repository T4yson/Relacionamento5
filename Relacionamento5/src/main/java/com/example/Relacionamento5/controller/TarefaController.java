package com.example.Relacionamento5.controller;

import com.example.Relacionamento5.dto.tarefa.TarefaRequest;
import com.example.Relacionamento5.dto.tarefa.TarefaResponse;
import com.example.Relacionamento5.service.TarefaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atividade5/tAREFAS")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TarefaResponse criarTarefa (@Valid @RequestBody TarefaRequest request) {
        return tarefaService.criarTarefa(request);
    }

    @GetMapping
    public List<TarefaResponse> listar(
            @RequestParam(required = false) Long projetoId,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String titulo
    ) {
        return tarefaService.listar(projetoId, id, titulo);
    }
}
