package com.example.controle.api.controller;

import com.example.controle.api.dtos.AdicionarAlunoTurmaDTO;
import com.example.controle.domain.model.Turma;
import com.example.controle.domain.service.TurmaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/turmas")
@RestController
public class TurmaController {

    private TurmaService turmaService;

    @PostMapping("/cadastrar")
    public Turma cadastrarNovaTurma(@RequestBody Turma turma) {
        return turmaService.cadastrarNovaTurma(turma);
    }

    @GetMapping("/listar")
    public List<Turma> listarTurmas() {
        return turmaService.todasTurmas();
    }

    @DeleteMapping("/excluir/{turmaId}")
    public String excluirTurma(@PathVariable long turmaId) {
        return turmaService.excluirTurma(turmaId);
    }

    @PutMapping("/adicionaraluno")
    public Turma adicionarAlunoTurma(@RequestBody AdicionarAlunoTurmaDTO infoAluno) {
        return turmaService.adicionarAlunoTurma(infoAluno.getTurma_id(), infoAluno.getAluno_id());
    }


}
