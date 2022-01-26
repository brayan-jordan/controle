package com.example.controle.domain.service;

import com.example.controle.api.exception.NegocioException;
import com.example.controle.domain.model.Turma;
import com.example.controle.domain.repository.TurmaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TurmaService {

    private TurmaRepository turmaRepository;

    public Turma cadastrarNovaTurma(Turma turma) {
        if (turmaRepository.existByName(turma.getNome()).isPresent()) {
            throw new NegocioException("Já existe uma turma com esse nome");
        }

        return turmaRepository.save(turma);
    }

}
