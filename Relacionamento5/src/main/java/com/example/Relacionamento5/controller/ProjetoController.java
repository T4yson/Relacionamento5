package com.example.Relacionamento5.controller;

import com.example.Relacionamento5.dto.projeto.ProjetoRequest;
import com.example.Relacionamento5.dto.projeto.ProjetoResponse;
import com.example.Relacionamento5.service.ProjetoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atividade5/projetos")
@RequiredArgsConstructor
public class ProjetoController {

    private final ProjetoService projetoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjetoResponse criarProjeto(@Valid @RequestBody ProjetoRequest request) {
        return projetoService.criarProjeto(request);
    }

    @GetMapping
    public List<ProjetoResponse> listar() {
        return projetoService.listar();
    }
}
