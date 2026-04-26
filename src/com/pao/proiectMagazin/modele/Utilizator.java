package com.pao.proiectMagazin.modele;

public interface Utilizator {


    public void reangajare(String dataAngajare);
    public void reangajare(String dataAngajare, String nume, String prenume, int salariu, String adresa, String telefon, String email, String parola);
    public void concediere(String dataConcediere);

    public String getUsername();
    public int getUid();
    public String getNume();
    public String getPrenume();
    public int getSalariu();
    public String getCnp();
    public String getAdresa();
    public String getTelefon();
    public String getEmail();
    public String getParola();
    public String getDataNasterii();
    public String getDataAngajare();
    public String getDataConcediere();
    public void printIntervaleAngajariPrecedente();
    public String toStringCompact();
    public String toStringDetaliat();
    public abstract String getRol();
    public boolean eAngajat();
}
