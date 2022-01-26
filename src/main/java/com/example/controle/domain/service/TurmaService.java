package com.example.controle.domain.service;

import com.example.controle.api.exception.NegocioException;
import com.example.controle.domain.model.Turma;
import com.example.controle.domain.repository.TurmaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TurmaService {

    private TurmaRepository turmaRepository;

    public List<Turma> todasTurmas() {
        return turmaRepository.findAll();
    }

    public Turma cadastrarNovaTurma(Turma turma) {
        if (turmaRepository.existByName(turma.getNome()).isPresent()) {
            throw new NegocioException("Já existe uma turma com esse nome");
        }

        return turmaRepository.save(turma);
    }

    public String excluirTurma(Long turmaId) {
        turmaRepository.delete(turmaRepository.findById(turmaId).orElseThrow(
                () -> new NegocioException("Turma nao encontrada"))
        );

        return "Turma excluida";
    }

}
