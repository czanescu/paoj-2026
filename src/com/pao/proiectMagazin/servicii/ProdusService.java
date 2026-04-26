package com.pao.proiectMagazin.servicii;

import com.pao.proiectMagazin.exceptii.ProdusNegasitException;
import com.pao.proiectMagazin.exceptii.StocInsuficientException;
import com.pao.proiectMagazin.modele.ModificareStocRecord;
import com.pao.proiectMagazin.modele.Produs;
import com.pao.proiectMagazin.modele.RaportVanzari;
import com.pao.proiectMagazin.modele.VanzareRecord;

import java.time.LocalDateTime;
import java.util.*;

public class ProdusService {
    private static ProdusService instance;
    private final Map<Integer, Produs> produse = new HashMap<>();
    private final RaportVanzari raportVanzari = new RaportVanzari();
    private final List<ModificareStocRecord> modificariStoc = new ArrayList<>();

    private ProdusService() {}

    public static synchronized ProdusService getInstance() {
        if (instance == null) {
            instance = new ProdusService();
        }
        return instance;
    }

    public void adaugaProdus(Produs produs) {
        produse.put(produs.getCodInventar(), produs);
    }

    public void stergeProdus(int codInventar) throws ProdusNegasitException {
        if (!produse.containsKey(codInventar)) {
            throw new ProdusNegasitException("Produsul cu codul " + codInventar + " nu există.");
        }
        produse.remove(codInventar);
    }
    public void addRecordModificareStoc(int codInventar, int cantitate, String motiv, int uidUtilizator) throws ProdusNegasitException {
        Produs produs = cautaDupaId(codInventar);
        ModificareStocRecord modificareStoc = new ModificareStocRecord(codInventar, produs.getNume(), produs.getStoc()-cantitate, produs.getStoc(), motiv, uidUtilizator);
        modificariStoc.add(modificareStoc);
    }

    public int getNrModificariStoc() {
        return modificariStoc.size();
    }

    public Produs cautaDupaId(int codInventar) throws ProdusNegasitException {
        Produs produs = produse.get(codInventar);
        if (produs == null) {
            throw new ProdusNegasitException("Produsul cu codul " + codInventar + " nu a fost găsit.");
        }
        return produs;
    }

    public List<Produs> listeazaToate() {
        return new ArrayList<>(produse.values());
    }

    public List<Produs> listeazaSortatDupaCod() {
        List<Produs> lista = new ArrayList<>(produse.values());
        Collections.sort(lista);
        return lista;
    }

    public List<Produs> listeazaSortatDupaCodDescrescator() {
        List<Produs> lista = new ArrayList<>(produse.values());
        lista.sort(Comparator.reverseOrder());
        return lista;
    }

    public List<Produs> listeazaSortatDupaNume() {
        List<Produs> lista = new ArrayList<>(produse.values());
        lista.sort(Comparator.comparing(Produs::getNume, String.CASE_INSENSITIVE_ORDER));
        return lista;
    }

    public List<Produs> listeazaSortatDupaNumeDescrescator() {
        List<Produs> lista = new ArrayList<>(produse.values());
        lista.sort(Comparator.comparing(Produs::getNume, String.CASE_INSENSITIVE_ORDER).reversed());
        return lista;
    }

    public void vindeProdus(int codInventar, int cantitate)
            throws ProdusNegasitException, StocInsuficientException {
        Produs produs = cautaDupaId(codInventar);
        if (cantitate <= 0) {
            throw new IllegalArgumentException("Cantitatea trebuie să fie pozitivă.");
        }
        if (cantitate > produs.getStoc()) {
            throw new StocInsuficientException("Stoc insuficient pentru produsul " + codInventar);
        }
        produs.vanzare(cantitate);
        VanzareRecord vanzare = new VanzareRecord(
                codInventar,
                produs.getNume(),
                cantitate,
                produs.getPretVanzare(),
                produs.getCategorie(),
                LocalDateTime.now()
        );
        raportVanzari.adaugaVanzare(vanzare);
    }

    public RaportVanzari getRaportVanzari() {
        return raportVanzari;
    }

    public void addModificareStoc(ModificareStocRecord modificareStoc) {
        modificariStoc.add(modificareStoc);
    }

    public List<ModificareStocRecord> getModificariStoc() {
        return modificariStoc;
    }
}
