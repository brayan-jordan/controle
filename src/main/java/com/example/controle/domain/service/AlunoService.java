package com.example.controle.domain.service;

import com.example.controle.api.exception.NegocioException;
import com.example.controle.domain.model.Aluno;
import com.example.controle.domain.model.Turma;
import com.example.controle.domain.repository.AlunoRepository;
import com.example.controle.domain.repository.TurmaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlunoService {

    private AlunoRepository alunoRepository;
    private TurmaRepository turmaRepository;

    public String excluirAluno(Long alunoId) {
        alunoRepository.delete(alunoRepository.findById(alunoId).orElseThrow(
                () -> new NegocioException("Aluno nao encontrado"))
        );

        return "Aluno excluido com sucesso";
    }

    public Aluno cadastrarAluno(Aluno aluno) {
        if (alunoRepository.existsByName(aluno.getNome()).isPresent()) {
            throw new NegocioException("Ja existe um aluno cadastrado com esse nome");
        }

        return alunoRepository.save(aluno);
    }

    public List<Aluno> listarAlunos() {
        return alunoRepository.findAll();
    }

    public Aluno adicionarAlunoTurma(Long alunoId, Long turmaId) {
        Aluno aluno = alunoRepository.findById(alunoId).orElseThrow(() -> new NegocioException(
                "Aluno nao encontrado"
        ));

        Turma turma = turmaRepository.findById(turmaId).orElseThrow(() -> new NegocioException(
            "Turma nao encontrada"
        ));

        if (aluno.getTurmas().stream().anyMatch(turmaFilter -> turmaFilter == turma)) {
            throw new NegocioException("Esse aluno ja esta nessa turma");
        }

        turma.getAlunos().add(aluno);
        turmaRepository.save(turma);
        return alunoRepository.save(aluno);
    }

    public List<Turma> turmasParaAdicionar(Long alunoId) {
        List<Turma> turmas = turmaRepository.findAll();
        Aluno aluno  = alunoRepository.findById(alunoId).orElseThrow(() -> new NegocioException(
                "Aluno não encontrado"
        ));

        return turmas.stream().filter(turma -> !turma.getAlunos().contains(aluno)).collect(Collectors.toList());

    }

}
