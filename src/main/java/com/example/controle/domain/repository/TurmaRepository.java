package com.example.controle.domain.repository;

import com.example.controle.domain.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

    @Query("SELECT t FROM Turma t where t.nome = ?1")
    Optional<Turma> existByName(String nome);

}
