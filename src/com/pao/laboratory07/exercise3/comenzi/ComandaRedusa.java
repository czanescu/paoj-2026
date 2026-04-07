package com.pao.laboratory07.exercise3.comenzi;

import com.pao.laboratory07.exercise1.StareComanda;

public final class ComandaRedusa extends Comanda {
    int discountProcent;

    public ComandaRedusa(String nume, double pret, int discount, String client) {
        this.nume = nume;
        this.client = client;
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
        return String.format("DISCOUNTED: %s, pret: %.2f lei (-%s%%) [%s] - client: %s", nume, pretFinal(), discountProcent, stare.getName(), client);
    }
    @Override
    public String getClient() {
        return client;
    }
    @Override
    public int getDiscountProcent() {
        return discountProcent;
    }
    @Override
    public String getTipComanda() {return "DISCOUNTED";}
}
