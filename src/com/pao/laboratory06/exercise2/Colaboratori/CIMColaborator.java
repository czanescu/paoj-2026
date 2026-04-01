package com.pao.laboratory06.exercise2.Colaboratori;

import com.pao.laboratory06.exercise2.IOperatiiCitireScriere;
import com.pao.laboratory06.exercise2.TipColaborator;

import java.util.Scanner;

public class CIMColaborator extends PersoanaFizica implements IOperatiiCitireScriere {

    public CIMColaborator(String nume, String prenume, double salariuBrutLunar, boolean bonus){
        this.nume = nume;
        this.prenume = prenume;
        this.salariuBrutLunar = salariuBrutLunar;
        this.bonus = bonus;
    }

    public CIMColaborator(String nume, String prenume, double salariuBrutLunar){
        this.nume = nume;
        this.prenume = prenume;
        this.salariuBrutLunar = salariuBrutLunar;
        this.bonus = false;
    }

    public CIMColaborator(){
    }

    private boolean bonus;
    protected double salariuBrutLunar;

    @Override
    public double calculeazaVenitNetAnual() {
        if (bonus) return this.salariuBrutLunar * 12 * 0.55 * 1.1;
        return this.salariuBrutLunar * 12 * 0.55;
    }

    @Override
    public TipColaborator getTip() {
        return TipColaborator.CIM;
    }

    @Override
    public void citeste(Scanner in){
        this.nume = in.next();
        this.prenume = in.next();
        this.salariuBrutLunar = in.nextDouble();

        this.bonus = false;
        if (in.hasNext("DA|NU")) {
            this.bonus = in.next().equalsIgnoreCase("DA");
        }
    }

    @Override
    public String tipContract() {
        return "CIM";
    }

    @Override
    public boolean areBonus() {
        return bonus;
    }
}
