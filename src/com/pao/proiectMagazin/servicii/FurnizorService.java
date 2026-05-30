package com.pao.proiectMagazin.servicii;

import com.pao.proiectMagazin.modele.Furnizor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class FurnizorService {
    private static FurnizorService instance;
    private final Map<String, Furnizor> furnizori = new HashMap<>();

    private FurnizorService() {}

    public static synchronized FurnizorService getInstance() {
        if (instance == null) {
            instance = new FurnizorService();
        }
        return instance;
    }

    public void adaugaFurnizor(Furnizor furnizor) {
        if (furnizor == null) {
            throw new IllegalArgumentException("furnizor nu poate fi null");
        }
        furnizori.put(furnizor.getCui(), furnizor);
    }

    public Furnizor cautaDupaCui(String cui) {
        Furnizor furnizor = furnizori.get(cui);
        if (furnizor == null) {
            throw new NoSuchElementException("Furnizorul cu CUI-ul " + cui + " nu a fost gasit.");
        }
        return furnizor;
    }

    public void stergeFurnizor(String cui) {
        if (!furnizori.containsKey(cui)) {
            throw new NoSuchElementException("Furnizorul cu CUI-ul " + cui + " nu exista.");
        }
        furnizori.remove(cui);
    }

    public List<Furnizor> listeazaToate() {
        return new ArrayList<>(furnizori.values());
    }

    public boolean existaCui(String cui) {
        return furnizori.containsKey(cui);
    }

    public void incarcaFurnizori(List<Furnizor> lista) {
        if (lista == null) {
            return;
        }
        for (Furnizor furnizor : lista) {
            if (furnizor != null) {
                furnizori.put(furnizor.getCui(), furnizor);
            }
        }
    }
}
