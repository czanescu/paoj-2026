package com.pao.laboratory06.exercise3.Indivizi;

import com.pao.laboratory06.exercise3.Interfete.PlataOnlineSMS;

import java.util.ArrayList;
import java.util.List;

public class PersoanaJuridica extends Persoana implements PlataOnlineSMS {
    List<String> smsTrimise;

    public PersoanaJuridica(String nume, String prenume, String telefon, double sold) {
        this.nume = nume;
        this.prenume = prenume;
        this.telefon = telefon;
        this.smsTrimise = new ArrayList<>();
        this.sold = sold;
    }

    //aceleași mențiuni ca la inginer pentru ce ține de PlataOnline
    @Override
    public void autentificare(String user, String parola){
        if (user == null || user.isBlank() || parola == null || parola.isBlank()) {
            throw new IllegalArgumentException("User-ul și parola nu pot fi nule sau goale.");
        }
        autentificat = true;
    }

    @Override
    public double consultareSold() {
        if (!autentificat) {
            throw new IllegalStateException("Trebuie să te autentifici înainte de a consulta soldul.");
        }
        return sold;
    }

    @Override
    public boolean efectuarePlata(double suma) {
        if (!autentificat) {
            throw new IllegalStateException("Trebuie să te autentifici înainte de a efectua o plată.");
        }
        if (suma <= 0) {
            trimiteSMS("S-a încercat plata în valoare de " + suma + ", sold actual " + sold + ".");
            throw new IllegalArgumentException("Suma trebuie să fie pozitivă.");
        }
        if (sold < suma) {
            return false;
        }
        sold -= suma;
        //singura diferență aici, unde adaug la listă plata
        trimiteSMS("Plata de " + suma + " lei a fost efectuată cu succes. Sold actual: " + sold + " lei.");
        return true;
    }


    @Override
    public boolean trimiteSMS(String mesaj){
        // probabil aș fi putut să verific câte cifre are pentru a fi măcar ceva care poate fi adevărat
        if (telefon == null || telefon.isBlank()){
            throw new IllegalArgumentException("Telefonul nu poate fi nul sau gol.");
        }
        if (mesaj == null || mesaj.isBlank()) {
            return false;
        }
        smsTrimise.add(mesaj);
        return true;
    }

    // cum am scris și în Persoana.java, la persoana juridica returnez doar numele
    @Override
    public String getNume(){
        return nume;
    }

    public void printSMS(){
        for (String sms : smsTrimise) {
            System.out.println(sms);
        }
    }
}
