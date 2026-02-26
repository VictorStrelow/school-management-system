package com.ctw.strelow.school_system.model;

public class Nota {

    private int id;
    private int aluno_id;
    private int aula_id;
    private double valor;

    // Construtor
    public Nota() {}

    public Nota(int id, int aluno_id, int aula_id, double nota) {
        this.id = id;
        this.aluno_id = aluno_id;
        this.aula_id = aula_id;
        this.valor = nota;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getAluno_id() {
        return aluno_id;
    }
    public void setAluno_id(int aluno_id) {
        this.aluno_id = aluno_id;
    }

    public int getAula_id() {
        return aula_id;
    }
    public void setAula_id(int aula_id) {
        this.aula_id = aula_id;
    }

    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }

}