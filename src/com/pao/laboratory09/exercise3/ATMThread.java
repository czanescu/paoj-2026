package com.pao.laboratory09.exercise3;

import java.util.Random;
import com.pao.laboratory09.exercise1.Tranzactie;

public class ATMThread extends Thread {
    private final int atmId;
    private final CoadaTranzactii coada;
    private final Random random = new Random();

    public ATMThread(int id, CoadaTranzactii coada) {
        this.atmId = id;
        this.coada = coada;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 4; i++) {
                int tranzactieId = atmId * 100 + i;
                double suma = 50 + (random.nextDouble() * 450);

                Tranzactie t = new Tranzactie(
                        tranzactieId,
                        suma,
                        "2026-05-03",
                        "Cont-ATM-" + atmId,
                        "Cont-Destinatie-" + i,
                        "CREDIT"
                );

                System.out.printf("[ATM-%d] trimite: Tranzactie #%d %.2f RON%n", atmId, tranzactieId, suma);
                coada.adauga(t, "ATM-" + atmId);
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            System.err.println("[ATM-" + atmId + "] a fost întrerupt.");
            Thread.currentThread().interrupt();
        }
    }
}