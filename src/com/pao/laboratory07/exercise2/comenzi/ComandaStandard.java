package com.pao.laboratory07.exercise2.comenzi;

import com.pao.laboratory07.exercise1.StareComanda;

public final class ComandaStandard extends Comanda{

    public ComandaStandard(String nume, double pret) {
        this.nume = nume;
        this.pret = pret;
        this.stare = StareComanda.PLACED;
    }

    @Override
    public double pretFinal() {
        return pret;
    }
    @Override
    public String descriere() {
        return String.format("STANDARD: %s, pret: %.2f lei [%s]", nume, pret, stare.getName());
    }
}
