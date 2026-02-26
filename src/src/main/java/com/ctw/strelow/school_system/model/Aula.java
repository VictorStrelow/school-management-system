package com.ctw.strelow.school_system.model;

import java.time.LocalDateTime;

public class Aula {

    private int id;
    private int turma_id;
    private LocalDateTime data_hora;
    private String assunto;

    // Construtor
    public Aula() {}

    public Aula(int id, int turma_id, LocalDateTime dataHora, String assunto) {
        this.id = id;
        this.turma_id = turma_id;
        this.data_hora = dataHora;
        this.assunto = assunto;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getTurma_id() {
        return turma_id;
    }
    public void setTurma_id(int turma_id) {
        this.turma_id = turma_id;
    }

    public LocalDateTime getData_hora() {
        return data_hora;
    }
    public void setData_hora(LocalDateTime data_hora) {
        this.data_hora = data_hora;
    }

    public String getAssunto() {
        return assunto;
    }
    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

}