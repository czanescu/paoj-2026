package com.pao.laboratory06.exercise2.Colaboratori;

import com.pao.laboratory06.exercise2.IOperatiiCitireScriere;
import com.pao.laboratory06.exercise2.TipColaborator;

public abstract class Colaborator implements IOperatiiCitireScriere {
    protected String nume;
    protected String prenume;

    public abstract double calculeazaVenitNetAnual();

    public abstract TipColaborator getTip();

    public void afiseaza(){
        System.out.printf("%s: %s %s, venit net anual: %.2f lei%n", tipContract(), this.nume, this.prenume, calculeazaVenitNetAnual());
    }

    public abstract String tipContract();

    public abstract boolean areBonus();
}
