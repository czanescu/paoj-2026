package com.pao.laboratory05.biblioteca.Comparatori;

import com.pao.laboratory05.biblioteca.Carte;

import java.util.Comparator;

public class CarteAutorComparator implements Comparator<Carte> {
    @Override
    public int compare(Carte c1, Carte c2) {
        return c1.getAutor().compareTo(c2.getAutor());
    }
}
