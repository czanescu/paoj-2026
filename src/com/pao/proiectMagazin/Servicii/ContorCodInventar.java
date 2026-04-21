package com.pao.proiectMagazin.Servicii;

public final class ContorCodInventar {
    private static volatile ContorCodInventar instance;
    private static boolean initializat = false;
    private int codInventar;

    private ContorCodInventar(int initial) {
        codInventar = initial;
    }

    public static void initialize(int initial) {
        if (initializat) {
            throw new IllegalStateException("ContorCodInventar a fost deja initializat");
        }
        instance = new ContorCodInventar(initial);
        initializat = true;
    }

    public static ContorCodInventar getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ContorCodInventar trebuie initializat inainte sa fie folosit");
        }
        return instance;
    }

    public int getCodInventar() {
        if (instance == null) {
            throw new IllegalStateException("ContorCodInventar is not initializat");
        }
        return codInventar++;
    }
}