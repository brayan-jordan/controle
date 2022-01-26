package com.example.controle.domain.repository;

import com.example.controle.domain.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Query("SELECT a From Aluno a WHERE a.nome = ?1")
    Optional<Aluno> existsByName(String nome);

}
