package com.pao.proiectMagazin.modele;

public abstract class Persoana implements Utilizator {
    protected String nume;
    protected String prenume;
    protected String username;// e teoretic si el uid, dar l-am adaugat dupa ce deja implementasem uid-ul
    protected String cnp;
    protected int uid;
    protected String adresa;
    protected String telefon;
    protected String email;
    protected String parola;
    protected String dataNasterii;

    public Persoana(String nume, String prenume, String username, String cnp, String adresa, String telefon, String email, String parola, int uid){
        this.nume = nume;
        this.prenume = prenume;
        this.username = username;
        this.cnp = cnp;
        this.adresa = adresa;
        this.telefon = telefon;
        this.email = email;
        this.parola = parola;
        this.uid = uid;
    }
    public abstract String getRol();
    public String getUsername(){return username;}
    public int getUid(){return uid;}
    public String getNume(){return nume;}
    public String getPrenume(){return prenume;}
    public String getCnp(){return cnp;}
    public String getAdresa(){return adresa;}
    public String getTelefon(){return telefon;}
    public String getEmail(){return email;}
    public String getParola(){return parola;}
    public String getDataNasterii(){return dataNasterii;}

    public String toStringCompact(){
        return String.format("%d, %s %s, %s", uid, nume, prenume, getRol());
    }
    public String toStringDetaliat(){
        return String.format("%d, %s, %s %s, cnp: %s, adresa: %s, telefon: %s, email: %s, data nasterii: %s", uid, username, nume, prenume, cnp, adresa, telefon, parola, dataNasterii);
    }
}
