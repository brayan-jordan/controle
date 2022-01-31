package com.example.controle.domain.repository;

import com.example.controle.domain.model.TurmaAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TurmaAlunoRepository extends JpaRepository<TurmaAluno, Long> {

    @Query("SELECT t FROM TurmaAluno t where t.turma_id = ?1 and t.aluno_id = ?2")
    TurmaAluno findByTurmaAndAluno(Long turmaId, Long alunoId);

    @Query("SELECT t FROM TurmaAluno t where t.turma_id = ?1")
    List<TurmaAluno> findByTurma(Long turmaId);

}
