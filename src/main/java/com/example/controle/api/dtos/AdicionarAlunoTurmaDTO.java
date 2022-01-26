package com.example.controle.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AdicionarAlunoTurmaDTO {

    private Long aluno_id;

    private Long turma_id;

}
