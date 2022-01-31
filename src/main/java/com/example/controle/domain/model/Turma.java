package com.example.controle.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "turmas")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToMany()
    @JoinTable(name = "turmas_alunos", joinColumns =
    @JoinColumn(name = "turma_id", referencedColumnName = "id"), inverseJoinColumns =
    @JoinColumn(name = "aluno_id", referencedColumnName = "id"))
    private List<Aluno> alunos;

}
