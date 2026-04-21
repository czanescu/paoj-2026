package com.pao.proiectMagazin.Modele;

public class Furnizor {
    String nume;
    String adresa;
    String telefon;
    String email;
    String cui;

    public Furnizor(String nume, String adresa, String telefon, String email, String cui){
        this.nume = nume;
        this.adresa = adresa;
        this.telefon = telefon;
        this.email = email;
        this.cui = cui;
    }

    public String getNume(){return nume;}
    public String getAdresa(){return adresa;}
    public String getTelefon(){return telefon;}
    public String getEmail(){return email;}
    public String getCui(){return cui;}
    public String toString(){return String.format("Nume: %s, Adresa: %s, Telefon: %s, Email: %s, CUI: %s", nume, adresa, telefon, email, cui);}

    public void setNume(String nume){this.nume = nume;}
    public void setAdresa(String adresa){this.adresa = adresa;}
    public void setTelefon(String telefon){this.telefon = telefon;}
    public void setEmail(String email){this.email = email;}
}
