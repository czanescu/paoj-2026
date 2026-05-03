package com.pao.laboratory09.exercise3;

public class Main {
    public static void main(String[] args) {
        CoadaTranzactii coadaPartajata = new CoadaTranzactii();

        ProcessorThread processorRunnable = new ProcessorThread(coadaPartajata);
        Thread consumerThread = new Thread(processorRunnable);
        consumerThread.start();

        ATMThread atm1 = new ATMThread(1, coadaPartajata);
        ATMThread atm2 = new ATMThread(2, coadaPartajata);
        ATMThread atm3 = new ATMThread(3, coadaPartajata);

        atm1.start();
        atm2.start();
        atm3.start();

        try {
            atm1.join();
            atm2.join();
            atm3.join();

            processorRunnable.opreste();

            synchronized (coadaPartajata) {
                coadaPartajata.notifyAll();
            }

            consumerThread.join();

        } catch (InterruptedException e) {
            System.err.println("Firul principal a fost întrerupt.");
            Thread.currentThread().interrupt();
        }

        System.out.println("----------------------------------------------");
        System.out.println("Toate tranzactiile procesate. Total: 12");
    }
}