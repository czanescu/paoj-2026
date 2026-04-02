package com.pao.laboratory06.exercise3;

import com.pao.laboratory06.exercise3.Indivizi.Inginer;

import java.util.Comparator;

public class ComparatorInginerSalariu implements Comparator<Inginer> {

    public int compare(Inginer a, Inginer b) {
        return Double.compare(b.getSalariu(), a.getSalariu());
    }
}
