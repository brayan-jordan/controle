package com.example.controle.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "turmas_alunos")
public class TurmaAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long aluno_id;

    private Long turma_id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "infoAluno")
    private List<DiaDeAula> diasDeAula;

}
