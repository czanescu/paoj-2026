package com.pao.laboratory06.exercise3.Indivizi;

public abstract class Persoana {
    protected String nume;
    protected String prenume;
    protected String telefon;
    protected Boolean autentificat;
    protected double sold;

    // am implementat getNume ca să ignor prenumele unde e cazul (la angajat returnează numele și prenumele, la pers jur. returnează doar numele)
    public abstract String getNume();
}
