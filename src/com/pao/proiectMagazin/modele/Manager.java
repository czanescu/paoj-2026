package com.pao.proiectMagazin.modele;

public class Manager extends Angajat{
    public Manager(String username,
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
        super(username, nume, prenume, salariu, cnp, adresa, telefon, email, parola, dataNasterii, dataAngajare, uid);
    }
    public String getRol(){return "Manager";}
}
