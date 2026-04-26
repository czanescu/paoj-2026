package com.pao.proiectMagazin.modele;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RaportVanzari {
    private final List<VanzareRecord> vanzari;

    public RaportVanzari() {
        this.vanzari = new ArrayList<>();
    }

    public void adaugaVanzare(VanzareRecord vanzareRecord) {
        if (vanzareRecord == null) {
            throw new IllegalArgumentException("vanzareRecord nu poate fi null");
        }
        vanzari.add(vanzareRecord);
    }

    public List<VanzareRecord> getVanzari() {
        return Collections.unmodifiableList(vanzari);
    }

    public List<VanzareRecord> ultimeleVanzari(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n trebuie să fie pozitiv");
        }
        int start = Math.max(0, vanzari.size() - n);
        return new ArrayList<>(vanzari.subList(start, vanzari.size()));
    }

    public int getTotalVanzari() {
        int total = 0;
        for (VanzareRecord vanzareRecord : vanzari) {
            total += vanzareRecord.getTotal();
        }
        return total;
    }

    public int getTotalUnitatiVandute() {
        int total = 0;
        for (VanzareRecord vanzareRecord : vanzari) {
            total += vanzareRecord.getCantitate();
        }
        return total;
    }

    public List<VanzareRecord> vanzariDupaInterval(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("intervalul nu poate avea valori null");
        }
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("end trebuie să fie după start");
        }

        List<VanzareRecord> rezultat = new ArrayList<>();
        for (VanzareRecord vanzareRecord : vanzari) {
            LocalDateTime ts = vanzareRecord.getTimestamp();
            if ((ts.isEqual(start) || ts.isAfter(start)) && (ts.isEqual(end) || ts.isBefore(end))) {
                rezultat.add(vanzareRecord);
            }
        }
        return rezultat;
    }

    public List<VanzareRecord> vanzariDupaCategorie(String categorie) {
        List<VanzareRecord> rezultat = new ArrayList<>();
        for (VanzareRecord vanzareRecord : vanzari) {

            if (vanzareRecord.getCategorieProdus().equals(categorie)){
                rezultat.add(vanzareRecord);
            }
        }
        return rezultat;
    }

    public List<VanzareRecord> vanzariDupaProdus(int codProdus) {
        List<VanzareRecord> rezultat = new ArrayList<>();
        for (VanzareRecord vanzareRecord : vanzari) {

            if (vanzareRecord.getCodProdus() == codProdus){
                rezultat.add(vanzareRecord);
            }
        }
        return rezultat;
    }

    @Override
    public String toString() {
        return String.format("RaportVanzari{numarVanzari=%d, totalUnitati=%d, totalLei=%d}",
                vanzari.size(), getTotalUnitatiVandute(), getTotalVanzari());
    }
}