package com.pao.laboratory07.exercise3.comenzi;

import com.pao.laboratory07.exercise1.StareComanda;

public final class ComandaStandard extends Comanda {

    public ComandaStandard(String nume, double pret, String client) {
        this.nume = nume;
        this.client = client;
        this.pret = pret;
        this.stare = StareComanda.PLACED;
    }

    @Override
    public double pretFinal() {
        return pret;
    }
    @Override
    public String descriere() {
        return String.format("STANDARD: %s, pret: %.2f lei [%s] - client: %s", nume, pret, stare.getName(), client);
    }
    @Override
    public String getClient() {
        return client;
    }
    @Override
    public int getDiscountProcent() {return 0;}
}
