package com.pao.laboratory09.exercise3;

import java.util.LinkedList;
import java.util.Queue;
import com.pao.laboratory09.exercise1.Tranzactie;

public class CoadaTranzactii {
    private final Queue<Tranzactie> coada = new LinkedList<>();
    private static final int CAPACITATE_MAXIMA = 5;

    public synchronized void adauga(Tranzactie t, String numeProducator) {
        while (coada.size() == CAPACITATE_MAXIMA) {
            try {
                System.out.println("Coada e plină (5/5). Aștept eliberarea înainte de a adăuga...");
                System.out.println("[" + numeProducator + "] astept loc...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Eroare: Adăugarea a fost întreruptă.");
                return;
            }
        }
        coada.add(t);
        System.out.println("[+] S-a adăugat: " + t.toString() + " | Tranzacții în coadă: " + coada.size());
        notifyAll();
    }

    public synchronized Tranzactie extrage() {
        while (coada.isEmpty()) {
            try {
                System.out.println("Coada e goală (0/5). Aștept adăugarea unei tranzacții...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Eroare: Extragerea a fost întreruptă.");
                return null;
            }
        }
        Tranzactie t = coada.poll();
        System.out.println("[-] S-a extras:  " + t.toString() + " | Tranzacții în coadă: " + coada.size());
        notifyAll();
        return t;
    }

    public synchronized boolean esteGoala() {
        return coada.isEmpty();
    }
}