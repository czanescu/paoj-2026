package com.pao.proiectMagazin.Modele;

import java.util.ArrayList;
import java.util.List;

public class Angajat implements Utilizator{
    String nume;
    String prenume;
    String username;// e teoretic si el uid, dar l-am adaugat dupa ce deja implementasem uid-ul
    int salariu;
    String cnp;
    int uid;
    String adresa;
    String telefon;
    String email;
    String parola;
    String dataNasterii;
    String dataAngajare;
    String dataConcediere;
    List<String> intervaleAngajariPrecedente;

    public Angajat(
            String username,
            String nume,
            String prenume,
            int salariu,
            String cnp,
            String adresa,
            String telefon,
            String email,
            String parola,
            String dataNasterii,
            String dataAngajare,
            int uid){
        this.username = username;
        this.nume = nume;
        this.prenume = prenume;
        this.salariu = salariu;
        this.cnp = cnp;
        this.adresa = adresa;
        this.telefon = telefon;
        this.email = email;
        this.parola = parola;
        this.dataNasterii = dataNasterii;
        this.dataAngajare = dataAngajare;
        this.dataConcediere = null;
        this.intervaleAngajariPrecedente = new ArrayList<>();
        this.uid = uid;
    }

    public void reangajare(String dataAngajare){
        this.dataAngajare = dataAngajare;
        this.dataConcediere = null;
    }

    public void reangajare(String dataAngajare, String nume, String prenume, int salariu, String adresa, String telefon, String email, String parola){
        this.dataAngajare = dataAngajare;
        this.dataConcediere = null;
        this.nume = nume;
        this.prenume = prenume;
        this.salariu = salariu;
        this.adresa = adresa;
        this.telefon = telefon;
        this.email = email;
        this.parola = parola;
    }

    public void concediere(String dataConcediere){
        this.dataConcediere = dataConcediere;
        String informatii;
        informatii = String.format("În intervalul %s - %s a lucrat în calitate de %s, pe salariul de %d", this.dataAngajare, this.dataConcediere, getRol(), this.salariu);
        intervaleAngajariPrecedente.add(informatii);
    }
    public String getUsername(){return username;}
    public int getUid(){return uid;}
    public String getNume(){return nume;}
    public String getPrenume(){return prenume;}
    public int getSalariu(){return salariu;}
    public String getCnp(){return cnp;}
    public String getAdresa(){return adresa;}
    public String getTelefon(){return telefon;}
    public String getEmail(){return email;}
    public String getParola(){return parola;}
    public String getDataNasterii(){return dataNasterii;}
    public String getDataAngajare(){return dataAngajare;}
    public String getDataConcediere(){return dataConcediere;}
    public boolean eAngajat(){if (dataConcediere == null) return true; else return false;}
    public void printIntervaleAngajariPrecedente(){
        if(intervaleAngajariPrecedente != null)
            for(String i : intervaleAngajariPrecedente)
                System.out.println(i);
        else
            System.out.println("Nu exista intervale de angajare precedente");
    }
    public String toStringCompact(){
        if (!eAngajat())
            return String.format("%d, %s %s, %s - demisionat", uid, nume, prenume, getRol());
        return String.format("%d, %s %s, %s", uid, nume, prenume, getRol());
    }
    public String toStringDetaliat(){
        if (dataConcediere != null)
            return String.format("%d, %s, %s %s, salariu: %s, cnp: %s, adresa: %s, telefon: %s, email: %s, data nasterii: %s, data angajare: %s, data concediere: %s", uid, username, nume, prenume, salariu, cnp, adresa, telefon, email, dataNasterii, dataAngajare, dataConcediere);
        return String.format("%d, %s, %s %s, salariu: %s, cnp: %s, adresa: %s, telefon: %s, email: %s, data nasterii: %s, data angajare: %s", uid, username, nume, prenume, salariu, cnp, adresa, telefon, parola, dataNasterii, dataAngajare);
    }

    public String getRol(){return "Angajat";}
}
