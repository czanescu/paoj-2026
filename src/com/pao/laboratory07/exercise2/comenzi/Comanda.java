package com.pao.laboratory07.exercise2.comenzi;

import com.pao.laboratory07.exercise1.StareComanda;

public abstract sealed class Comanda permits ComandaStandard, ComandaRedusa, ComandaGratuita {
    protected String nume;
    protected double pret;
    protected StareComanda stare;

    public abstract double pretFinal();
    public abstract String descriere();
}
