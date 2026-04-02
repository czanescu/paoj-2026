package com.pao.laboratory06.exercise3.Indivizi;

public class Angajat extends Persoana{
    protected double salariu;

    public Double getSalariu(){return salariu;}
    // aș fi putut să implementez direct în inginer trimiteSMS și getNume, dar m-am gândit că ar trebui să fie comune dacă ar fi mai multe tipuri de angajați, deci le-am lăsat aici
    public boolean trimiteSMS(String mesaj){throw new UnsupportedOperationException("Angajatul nu are permisiunea de trimite SMS");}
    public String getNume(){return nume + " " + prenume;}



}
