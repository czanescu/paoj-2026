package com.pao.proiectMagazin.modele;

public class Furnizor {
    private String nume;
    private String adresa;
    private String telefon;
    private String email;
    private String cui;

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

    @Override
    public boolean equals(Object obj){
        if (obj instanceof Furnizor) {
            Furnizor furnizor = (Furnizor) obj;
            return furnizor.cui.equals(this.cui);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return cui.hashCode();
    }
}
