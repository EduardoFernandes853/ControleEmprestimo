package br.edu.ifpa.laboratorio.model;

import java.time.LocalDateTime;

public class Emprestimo {
    private int id;
    private Aluno aluno;
    private Equipamento equipamento;
    private LocalDateTime dataEmprestimo;
    private LocalDateTime dataDevolucao;
    private String status;

    public Emprestimo() {}

    public Emprestimo(int id, Aluno aluno, Equipamento equipamento, LocalDateTime dataEmprestimo, LocalDateTime dataDevolucao, String status) {
        this.id = id;
        this.aluno = aluno;
        this.equipamento = equipamento;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public Equipamento getEquipamento() { return equipamento; }
    public void setEquipamento(Equipamento equipamento) { this.equipamento = equipamento; }

    public LocalDateTime getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDateTime dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }

    public LocalDateTime getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDateTime dataDevolucao) { this.dataDevolucao = dataDevolucao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}