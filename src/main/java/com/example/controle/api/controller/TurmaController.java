package com.example.controle.api.controller;

import com.example.controle.domain.model.Turma;
import com.example.controle.domain.service.TurmaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("/turmas")
@RestController
public class TurmaController {

    private TurmaService turmaService;

    @RequestMapping("/cadastrar")
    public Turma cadastrarNovaTurma(@RequestBody Turma turma) {
        return turmaService.cadastrarNovaTurma(turma);
    }

}
