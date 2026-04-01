package com.example.Relacionamento5.service;

import com.example.Relacionamento5.dto.projeto.ProjetoRequest;
import com.example.Relacionamento5.dto.projeto.ProjetoResponse;
import com.example.Relacionamento5.model.Projeto;
import com.example.Relacionamento5.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    public ProjetoResponse criarProjeto(ProjetoRequest request) {
        Projeto projeto = new Projeto();
        projeto.setNome(request.nome());
        projeto.setDescricao(request.descricao());
        return toResponse(projetoRepository.save(projeto));
    }

    public List<ProjetoResponse> listar() {
        return projetoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public Projeto findEntityById(Long id) {
        return projetoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado: " + id));
    }

    public ProjetoResponse toResponse(Projeto projeto) {
        return new ProjetoResponse(projeto.getId(), projeto.getNome(), projeto.getDescricao());
    }
}
