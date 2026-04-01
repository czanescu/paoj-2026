package com.pao.laboratory06.exercise2.Colaboratori;

import com.pao.laboratory06.exercise2.TipColaborator;

import java.util.Scanner;

public class SRLColaborator extends PersoanaJuridica{
    public SRLColaborator(String nume, String prenume, double venitLunar, double cheltuieliLunare){
        this.nume = nume;
        this.prenume = prenume;
        this.venitLunar = venitLunar;
        this.cheltuieliLunare = cheltuieliLunare;
    }
    public SRLColaborator(){
    }

    private double venitLunar;
    private double cheltuieliLunare;

    public double calculeazaVenitNetAnual(){
        return (venitLunar - cheltuieliLunare) * 12 * 0.84;
    }
    public TipColaborator getTip() {return TipColaborator.SRL;}
    @Override
    public void citeste(Scanner in){
        this.nume = in.next();
        this.prenume = in.next();
        this.venitLunar = in.nextDouble();
        this.cheltuieliLunare = in.nextDouble();
    }

    @Override
    public String tipContract() {
        return "SRL";
    }

    @Override
    public boolean areBonus() {
        return false;
    }
}
