CREATE TABLE alunos (
    id BIGINT AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    idade INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE turmas (
    id BIGINT AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE turmas_alunos (
    id BIGINT AUTO_INCREMENT,
    turma_id BIGINT NOT NULL,
    aluno_id BIGINT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE dias_de_aula (
    id BIGINT AUTO_INCREMENT,
    turma_aluno_id BIGINT NOT NULL,
    data_aula DATE NOT NULL,
    status_presenca VARCHAR(3),
    PRIMARY KEY (id)
);

ALTER TABLE dias_de_aula ADD CONSTRAINT fk_dia_de_aula
FOREIGN KEY (turma_aluno_id) REFERENCES turmas_alunos (id);

ALTER TABLE turmas_alunos ADD CONSTRAINT fk_turma_id
FOREIGN KEY (turma_id) REFERENCES turmas (id);

ALTER TABLE turmas_alunos ADD CONSTRAINT fk_aluno_id
FOREIGN KEY (aluno_id) REFERENCES alunos (id);