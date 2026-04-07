package com.pao.laboratory07.exercise3.comenzi;

import com.pao.laboratory07.exercise1.StareComanda;

public abstract sealed class Comanda permits ComandaStandard, ComandaRedusa, ComandaGratuita {
    protected String nume;
    protected String client;
    protected double pret;
    protected StareComanda stare;

    public abstract double pretFinal();
    public abstract String descriere();
    public abstract String getClient();
    public abstract int getDiscountProcent();
    public abstract String getTipComanda();
}
