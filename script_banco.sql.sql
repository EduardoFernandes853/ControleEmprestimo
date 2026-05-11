-- 1. Criação da Base de Dados
CREATE DATABASE IF NOT EXISTS controle_laboratorio;
USE controle_laboratorio;

-- 2. Criação da Tabela Aluno
CREATE TABLE aluno (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    matricula VARCHAR(20) NOT NULL UNIQUE
);

-- 3. Criação da Tabela Equipamento
CREATE TABLE equipamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    disponivel BOOLEAN DEFAULT TRUE
);

-- 4. Criação da Tabela Emprestimo
CREATE TABLE emprestimo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_aluno INT NOT NULL,
    id_equipamento INT NOT NULL,
    data_emprestimo DATETIME NOT NULL,
    data_devolucao DATETIME,
    status VARCHAR(20) DEFAULT 'ATIVO',
    
    FOREIGN KEY (id_aluno) REFERENCES aluno(id),
    FOREIGN KEY (id_equipamento) REFERENCES equipamento(id)
);

-- 5. Inserindo Dados Iniciais para Teste
INSERT INTO aluno (nome, matricula) VALUES 
('João Silva', '2023001'), 
('Maria Oliveira', '2023002');

INSERT INTO equipamento (nome, descricao, disponivel) VALUES 
('Projetor Epson', 'Projetor HD da sala 101', TRUE), 
('Notebook Dell', 'Core i5, 8GB RAM', TRUE),
('Microscópio Óptico', 'Lente 1000x', TRUE);

INSERT INTO emprestimo (id_aluno, id_equipamento, data_emprestimo, status) VALUES 
(1, 1, NOW(), 'ATIVO');

UPDATE equipamento SET disponivel = FALSE WHERE id = 1;