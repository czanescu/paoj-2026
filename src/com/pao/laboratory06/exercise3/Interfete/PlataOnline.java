package com.pao.laboratory06.exercise3.Interfete;

public interface PlataOnline {
    void autentificare(String user, String parola);
    double consultareSold();
    boolean efectuarePlata(double suma);
}
