package com.pao.laboratory07.exercise3.comenzi;

import com.pao.laboratory07.exercise1.StareComanda;

public final class ComandaGratuita extends Comanda {
    public ComandaGratuita(String nume, String client) {
        this.nume = nume;
        this.client = client;
        this.pret = 0;
        this.stare = StareComanda.PLACED;
    }

    @Override
    public double pretFinal() {
        return 0;
    }
    @Override
    public String descriere() {
        return String.format("GIFT: %s, gratuit [%s] - client: %s", nume, stare.getName(), client);
    }
    @Override
    public String getClient() {
        return client;
    }
    @Override
    public int getDiscountProcent() {return 100;}
}
