package com.pao.laboratory10.exercise1;

public class Tranzactie {
    public int id;
    public double suma;
    public String data;
    public TipTranzactie tip;
    String contSursa;

    public Tranzactie(int id, double suma, String data, String tipTranzactie) {
        this.id = id;
        this.suma = suma;
        this.data = data;
        if (tipTranzactie.equalsIgnoreCase("CREDIT")) tip = TipTranzactie.CREDIT;
        else if (tipTranzactie.equalsIgnoreCase("DEBIT")) tip = TipTranzactie.DEBIT;
        else throw new IllegalArgumentException("Inputul " + tipTranzactie + " pentru tipul tranzactiei nu este permis.");
    }

    public Tranzactie(int id, double suma, String data, String tipTranzactie, String contSursa) {
        this.id = id;
        this.suma = suma;
        this.data = data;
        this.contSursa = contSursa;
        if (tipTranzactie.equalsIgnoreCase("CREDIT")) tip = TipTranzactie.CREDIT;
        else if (tipTranzactie.equalsIgnoreCase("DEBIT")) tip = TipTranzactie.DEBIT;
        else throw new IllegalArgumentException("Inputul " + tipTranzactie + " pentru tipul tranzactiei nu este permis.");
    }

    public int getId() {return id;}
    public double getSuma() {return suma;}
    public String getData() {return data;}
    public TipTranzactie getTip() {return tip;}
    public  String getContSursa() {return contSursa;}

    @Override
    public String toString() {
        String tipTranzactie;
        if (tip == TipTranzactie.CREDIT)  tipTranzactie = "CREDIT";
        else tipTranzactie = "DEBIT";
        return String.format("[%d] %s %s: %.2f RON",  id, data, tipTranzactie, suma);
    }
}
