package com.example.controle.api.controller;

import com.example.controle.api.dtos.AdicionarAlunoTurmaDTO;
import com.example.controle.domain.model.Aluno;
import com.example.controle.domain.model.Turma;
import com.example.controle.domain.service.AlunoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/alunos")
@RestController
@AllArgsConstructor
public class AlunoController {

    private AlunoService alunoService;

    @PostMapping("/cadastrar")
    public Aluno cadastrarAluno(@RequestBody Aluno aluno) {
        return alunoService.cadastrarAluno(aluno);
    }

    @DeleteMapping("/excluir/{alunoId}")
    public String excluirAluno(@PathVariable long alunoId) {
        return alunoService.excluirAluno(alunoId);
    }

    @GetMapping("/listar")
    public List<Aluno> listarTodosAlunos() {
        return alunoService.listarAlunos();
    }

    @PutMapping("/adicionarnaturma")
    public Aluno adicionarAluno(@RequestBody AdicionarAlunoTurmaDTO infoAlunoAdicionar) {
        return alunoService.adicionarAlunoTurma(infoAlunoAdicionar.getAluno_id(), infoAlunoAdicionar.getTurma_id());
    }

    @GetMapping("/turmasparaadicionar/{alunoId}")
    public List<Turma> turmasParaAdicionar(@PathVariable Long alunoId) {
        return alunoService.turmasParaAdicionar(alunoId);
    }

    @PutMapping("/editar/{alunoId}")
    public Aluno editarAluno(@RequestBody Aluno aluno, @PathVariable Long alunoId) {
        return alunoService.editarAluno(alunoId, aluno);
    }

    @GetMapping("/findbyid/{alunoId}")
    public Aluno buscarAluno(@PathVariable Long alunoId) {
        return alunoService.findById(alunoId);
    }

}
