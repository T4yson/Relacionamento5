package com.example.Relacionamento5.service;

import com.example.Relacionamento5.dto.tarefa.TarefaRequest;
import com.example.Relacionamento5.dto.tarefa.TarefaResponse;
import com.example.Relacionamento5.model.Projeto;
import com.example.Relacionamento5.model.Tarefa;
import com.example.Relacionamento5.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final ProjetoService projetoService;

    public TarefaResponse criarTarefa (TarefaRequest request) {
        Projeto projeto = projetoService.findEntityById(request.projetoId());
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(request.titulo());
        tarefa.setStatus(request.status());
        tarefa.setProjeto(projeto);
        return toResponse(tarefaRepository.save(tarefa));
    }

    public List<TarefaResponse> listar(Long projetoId, Long id, String titulo) {
        if (id != null && titulo != null && !titulo.isBlank()) {
            return tarefaRepository.findByIdAndTituloContainingIgnoreCase(id, titulo)
                    .map(this::toResponse)
                    .stream()
                    .toList();
        }
        if (projetoId != null) {
            return tarefaRepository.findByProjetoId(projetoId).stream().map(this::toResponse).toList();
        }
        return tarefaRepository.findAll().stream().map(this::toResponse).toList();
    }

    private TarefaResponse toResponse (Tarefa tarefa) {
        return  new TarefaResponse(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getStatus(),
                tarefa.getProjeto().getId(),
                tarefa.getProjeto().getNome()
        );
    }
}
