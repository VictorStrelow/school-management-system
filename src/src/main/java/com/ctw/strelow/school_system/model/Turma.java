package com.ctw.strelow.school_system.model;

public class Turma {

    private int id;
    private String nome;
    private int curso_id;
    private int professor_id;

    // Contrutor
    public Turma() {}

    public Turma(int id, String nome, int curso_id, int professor_id) {
        this.id = id;
        this.nome = nome;
        this.curso_id = curso_id;
        this.professor_id = professor_id;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCurso_id() {
        return curso_id;
    }
    public void setCurso_id(int curso_id) {
        this.curso_id = curso_id;
    }

    public int getProfessor_id() {
        return professor_id;
    }
    public void setProfessor_id(int professor_id) {
        this.professor_id = professor_id;
    }

}