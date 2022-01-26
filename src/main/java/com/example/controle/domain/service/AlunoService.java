package com.example.controle.domain.service;

import com.example.controle.api.exception.NegocioException;
import com.example.controle.domain.model.Aluno;
import com.example.controle.domain.repository.AlunoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AlunoService {

    private AlunoRepository alunoRepository;

    private Aluno cadastrarAluno(Aluno aluno) {
        if (alunoRepository.existsByName(aluno.getNome()).isPresent()) {
            throw new NegocioException("Ja existe um aluno cadastrado com esse nome");
        }

        return alunoRepository.save(aluno);
    }

    private String excluirAluno(Long alunoId) {
        alunoRepository.delete(alunoRepository.findById(alunoId).orElseThrow(
                () -> new NegocioException("Aluno nao encontrado"))
        );

        return "Aluno excluido com sucesso";
    }

}
