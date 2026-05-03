package com.pao.laboratory09.exercise3;

import com.pao.laboratory09.exercise1.Tranzactie;

public class ProcessorThread implements Runnable {
    private final CoadaTranzactii coada;

    private volatile boolean activ = true;

    public ProcessorThread(CoadaTranzactii coada) {
        this.coada = coada;
    }

    public void opreste() {
        this.activ = false;
    }

    @Override
    public void run() {
        System.out.println("[Processor] Pornit și gata de procesare...");

        try {
            while (activ || !coada.esteGoala()) {
                Tranzactie t = coada.extrage();

                if (t != null) {
                    System.out.printf("[Processor] Factura #%d - %.2f RON | %s%n",
                            t.getId(), t.getSuma(), t.getData());
                    t.setNote();
                }
                Thread.sleep(80);
            }
        } catch (InterruptedException e) {
            System.out.println("[Processor] A fost întrerupt în timpul somnului.");
            Thread.currentThread().interrupt();
        }

        System.out.println("[Processor] Și-a încheiat activitatea.");
    }
}