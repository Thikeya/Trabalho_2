package com.example.trabalho_2.db;

import java.io.Serializable;

public class Time implements Serializable {
    private int timeId;
    private String timeDescricao;

    public Time(){}

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    public String getTimeDescricao() {
        return timeDescricao;
    }

    public void setTimeDescricao(String timeDescricao) {
        this.timeDescricao = timeDescricao;
    }

    public String toString() {
        return "Descricao: " + timeDescricao.toString() + " - ID: " + timeId;
    }
}
