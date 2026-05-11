package br.edu.ifpa.laboratorio.model;

public class Aluno {
    private int id;
    private String nome;
    private String matricula;

    // Construtor vazio
    public Aluno() {}

    // Construtor com parâmetros
    public Aluno(int id, String nome, String matricula) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
}