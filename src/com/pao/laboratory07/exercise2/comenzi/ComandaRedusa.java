package com.pao.laboratory07.exercise2.comenzi;

import com.pao.laboratory07.exercise1.StareComanda;

public final class ComandaRedusa extends Comanda {
    int discountProcent;

    public ComandaRedusa(String nume, double pret, int discount) {
        this.nume = nume;
        this.pret = pret;
        this.discountProcent = discount;
        this.stare = StareComanda.PLACED;
    }

    @Override
    public double pretFinal() {
        return pret * (1 - discountProcent / 100.0);
    }
    @Override
    public String descriere() {
        return String.format("DISCOUNTED: %s, pret: %.2f lei (-%s%%) [%s]", nume, pretFinal(), discountProcent, stare.getName());
    }
}
