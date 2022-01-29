package com.example.controle.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlunoInfo {

    private String nome;

    private int idade;

    private double frequencia;

    private int numeroAulas;

}
