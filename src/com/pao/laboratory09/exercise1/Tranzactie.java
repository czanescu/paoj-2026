package com.pao.laboratory09.exercise1;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Tranzactie implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private double suma;
    private String data;
    private String contSursa;
    private String contDestinatie;
    private TipTranzactie tip;
    transient String note;

    public Tranzactie(int id, double suma, String data, String contSursa, String contDestinatie, String tip)
    {
        this.id = id;
        this.suma = suma;
        this.data = data;
        this.contSursa = contSursa;
        this.contDestinatie = contDestinatie;
        if (tip.equalsIgnoreCase("CREDIT")) this.tip = TipTranzactie.CREDIT;
        else if (tip.equalsIgnoreCase("DEBIT")) this.tip = TipTranzactie.DEBIT;
        else throw new IllegalArgumentException("Tip-ul introdus " + tip + " nu este valid.");
    }

    public static boolean esteDataValida(String dataString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(dataString, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public void setNote()
    {
        note = "procesat";
    }

    public String toString(){
        return String.format("[%d] %s %s: %.2f RON | %s -> %s", id, data, tip.toString(), suma, contSursa, contDestinatie);
    }

    public String getData(){
        return data;
    }
    public int getId(){
        return id;
    }

}