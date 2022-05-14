package com.example.trabalho_2.db;

import java.io.Serializable;

public class Jogador implements Serializable {
    private int jogadorId, timeId, jogadorAnoNascimento;
    private String jogadorNome, jogadorCpf;

    public Jogador(){ }

    public int getJogadorId() {
        return jogadorId;
    }

    public void setJogadorId(int jogadorId) {
        this.jogadorId = jogadorId;
    }

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    public int getJogadorAnoNascimento() {
        return jogadorAnoNascimento;
    }

    public void setJogadorAnoNascimento(int jogadorAnoNascimentor) { this.jogadorAnoNascimento = jogadorAnoNascimento; }

    public String getJogadorNome() {
        return jogadorNome;
    }

    public void setJogadorNome(String jogadorNome) {
        this.jogadorNome = jogadorNome;
    }

    public String getJogadorCpf() {
        return jogadorCpf;
    }

    public void setJogadorCpf(String jogadorCpf) {
        this.jogadorCpf = jogadorCpf;
    }

    public String toString() {
        return "Nome: " + jogadorNome.toString() + " - Time ID: " + timeId + " - CPF: " + jogadorCpf + " - Ano de nascimento: " + jogadorAnoNascimento;
    }
}
