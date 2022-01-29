package com.example.controle.domain.service;

import com.example.controle.api.dtos.AlunoInfo;
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
import java.util.ArrayList;
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

    public List<AlunoInfo> buscarInfoTurma(Long turmaId) {
        List<AlunoInfo> alunoInfos = new ArrayList<>();
        Turma turma = turmaRepository.findById(turmaId).orElseThrow(() -> new NegocioException(
                "Turma nao encontrada"
        ));

        List<TurmaAluno> turmaAlunos = new ArrayList<>();

        turma.getAlunos().forEach(aluno -> {
            turmaAlunos.add(turmaAlunoRepository.findByTurmaAndAluno(turma.getId(), aluno.getId()));
        });

        turmaAlunos.forEach(turmaAluno -> {
            Aluno aluno = alunoRepository.findById(turmaAluno.getAluno_id()).orElseThrow(() -> new NegocioException(
                    "exception"
            ));

            alunoInfos.add(new AlunoInfo(aluno.getNome(), aluno.getIdade(), calcularFrequencia(turmaAluno), turmaAluno.getDiasDeAula().size()));
        });

        return alunoInfos;
    }

    private int calcularFrequencia(TurmaAluno turmaAluno) {
        int quantidadePresentes2 = 0;
        for (int i =0; i < turmaAluno.getDiasDeAula().size(); ++i) {
            if (turmaAluno.getDiasDeAula().get(i).getStatusPresenca().equals(StatusPresenca.PRE)) {
                quantidadePresentes2 = quantidadePresentes2 + 1;
            }
        }

        int fator = quantidadePresentes2 * 100;
        return fator / turmaAluno.getDiasDeAula().size();
    }

    private StatusPresenca converterStatusPresenca(boolean isPresent) {
        if (isPresent) {
            return StatusPresenca.PRE;
        }

        return StatusPresenca.AUS;
    }

}
