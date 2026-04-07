package com.pao.laboratory07.exercise2.comenzi;

import com.pao.laboratory07.exercise1.StareComanda;

public final class ComandaGratuita extends Comanda {
    public ComandaGratuita(String nume) {
        this.nume = nume;
        this.pret = 0;
        this.stare = StareComanda.PLACED;
    }

    @Override
    public double pretFinal() {
        return 0;
    }
    @Override
    public String descriere() {
        return String.format("GIFT: %s, gratuit [%s]", nume, stare.getName());
    }
}
