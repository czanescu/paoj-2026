package com.pao.laboratory05.biblioteca.Comparatori;

import com.pao.laboratory05.biblioteca.Carte;

import java.util.Comparator;

public class CarteAnComparator implements Comparator<Carte> {
    @Override
    public int compare(Carte c1, Carte c2) {
        return Integer.compare(c1.getAn(), c2.getAn());
    }
}
