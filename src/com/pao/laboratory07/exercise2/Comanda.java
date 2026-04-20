package com.pao.laboratory07.exercise2;

public abstract sealed class Comanda permits ComandaStandard, ComandaRedusa, ComandaGratuita {
    protected String nume;

    public Comanda(String nume) {
        this.nume = nume;
    }

    public abstract double pretFinal();
    public abstract String descriere();
}