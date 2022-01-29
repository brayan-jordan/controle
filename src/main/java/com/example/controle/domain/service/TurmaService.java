package com.example.controle.domain.service;

import com.example.controle.api.dtos.ChamadaAlunoDTO;
import com.example.controle.api.exception.NegocioException;
import com.example.controle.domain.model.*;
import com.example.controle.domain.repository.AlunoRepository;
import com.example.controle.domain.repository.DiaDeAulaRepository;
import com.example.controle.domain.repository.TurmaAlunoRepository;
import com.example.controle.domain.repository.TurmaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TurmaService {

    private TurmaRepository turmaRepository;
    private AlunoRepository alunoRepository;
    private DiaDeAulaRepository diaDeAulaRepository;
    private TurmaAlunoRepository turmaAlunoRepository;

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

    public Turma adicionarAlunoTurma(Long turmaId, Long alunoId) {
        Aluno aluno = alunoRepository.findById(alunoId).orElseThrow(() -> new NegocioException(
                "Aluno nao encontrado"
        ));

        Turma turma = turmaRepository.findById(turmaId).orElseThrow(() -> new NegocioException(
                "Turma nao encontrada"
        ));

        if (turma.getAlunos().stream().anyMatch(alunoFilter -> alunoFilter == aluno)) {
            throw new NegocioException("Esse aluno ja esta nessa turma");
        }

        turma.getAlunos().add(aluno);
        return turmaRepository.save(turma);
    }

    private List<Aluno> alunosParaAdicinar(Long turmaId) {
        List<Aluno> todosAlunos = alunoRepository.findAll();
        Turma turma = turmaRepository.findById(turmaId).orElseThrow(() -> new NegocioException(
                "Turma  nao encontrada"
        ));

        return todosAlunos.stream().filter(aluno -> !aluno.getTurmas().contains(turma)).collect(Collectors.toList());
    }

    public String fazerChamadaTurma(Long turmaId, List<ChamadaAlunoDTO> infoAlunos) {
        infoAlunos.forEach(infoAluno -> {
            TurmaAluno turmaAluno = turmaAlunoRepository.findByTurmaAndAluno(turmaId, infoAluno.getAlunoId());
            DiaDeAula diaDeAula = new DiaDeAula(null, turmaAluno, LocalDate.now(), converterStatusPresenca(infoAluno.isPresent()));
            diaDeAulaRepository.save(diaDeAula);
        });

        return "Chamada realizada";
    }

    private StatusPresenca converterStatusPresenca(boolean isPresent) {
        if (isPresent) {
            return StatusPresenca.PRE;
        }

        return StatusPresenca.AUS;
    }

}
