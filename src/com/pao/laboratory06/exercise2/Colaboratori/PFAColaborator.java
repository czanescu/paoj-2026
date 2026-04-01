package com.pao.laboratory06.exercise2.Colaboratori;

import com.pao.laboratory06.exercise2.IOperatiiCitireScriere;
import com.pao.laboratory06.exercise2.TipColaborator;

import java.util.Scanner;

public class PFAColaborator extends PersoanaFizica implements IOperatiiCitireScriere {
    public PFAColaborator(String nume, String prenume, double venitLunar, double cheltuieliLunare){
        this.nume = nume;
        this.prenume = prenume;
        this.venitLunar = venitLunar;
        this.cheltuieliLunare = cheltuieliLunare;
    }
    public PFAColaborator(){
    }

    private double venitLunar;
    private double cheltuieliLunare;

    @Override
    public double calculeazaVenitNetAnual(){ // am stat 30 de minute să fac debugging pentru că cifrele din fișierul de testare sunt calculate cu venitul lunar în loc de venitul net
        double venitNet = (venitLunar - cheltuieliLunare) * 12;
        double impozit = venitNet * 0.1;
        double CASS, CAS;
        if(venitLunar < 6 * 4050 * 12) CASS = 0.1 * 6 * 4050 * 12;
        else if(venitLunar >= 6 * 4050 * 12 && venitLunar < 72 * 4050 * 12) CASS = venitNet * 0.1;
        else CASS = 10 * 72 * 4050 * 12;

        if(venitLunar < 12 * 4050 * 12) CAS = 0;
        else if(venitLunar >= 12 * 4050 * 12 && venitLunar <= 24 * 4050 * 12) CAS = 0.25 * 12 * 4050 * 12;
        else CAS = 0.25 * 24 * 4050 * 12;
        return venitNet - impozit - CASS - CAS;
    }

    @Override
    public TipColaborator getTip() {
        return TipColaborator.PFA;
    }

    @Override
    public void citeste(Scanner in){
        this.nume = in.next();
        this.prenume = in.next();
        this.venitLunar = in.nextDouble();
        this.cheltuieliLunare = in.nextDouble();
    }

    @Override
    public String tipContract() {
        return "PFA";
    }

    @Override
    public boolean areBonus() {
        return false;
    }
}
