package com.example.moneycontrol;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Transacao {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public double valor;
    public String tipo; // "Receita" ou "Despesa"

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Transacao(double valor, String tipo) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public Transacao() {}
}
