package com.pao.laboratory05.biblioteca;

import com.pao.laboratory02.exercise.CarService;

import java.util.Arrays;
import java.util.Comparator;

public class BibliotecaService {
    private BibliotecaService() {
        this.carti = new Carte[0];
    }
    private static class Holder {
        private static final BibliotecaService INSTANCE = new BibliotecaService();
    }
    public static BibliotecaService getInstance() {
        return BibliotecaService.Holder.INSTANCE;
    }

    public void addCarte(Carte carte){
        Carte[] newArr = new Carte[this.carti.length + 1];
        System.arraycopy(this.carti, 0, newArr, 0, this.carti.length);
        newArr[this.carti.length] = carte;
        this.carti = newArr;
        System.out.println("S-a adăugat cartea: " + carte.getTitlu() + ", de autorul: " + carte.getAutor() + ".");
    }

    public void listSortedByRating(){
        Carte[] cartiSortate = new Carte[this.carti.length];
        System.arraycopy(carti, 0, cartiSortate, 0, carti.length);
        Arrays.sort(cartiSortate);
        for (Carte carte : cartiSortate) {
            System.out.println(carte);
        }
    }

    public void listSortedBy(Comparator<Carte> comparator){
        Carte[] cartiSortate = new Carte[this.carti.length];
        System.arraycopy(carti, 0, cartiSortate, 0, carti.length);
        Arrays.sort(cartiSortate, comparator);
        for (Carte carte : cartiSortate) {
            System.out.println(carte);
        }
    }

    private Carte[] carti;
}
