package com.pao.laboratory10.exercise1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Folosește LinkedList<Tranzactie> ca structură internă.
        LinkedList<Tranzactie> QueueTranzactii = new LinkedList<>();
        // Citește comenzi din stdin până la EOF:
        //
        //   ENQUEUE id suma data tip   → addLast  (niciun output)
        //   DEQUEUE                    → removeFirst sau "Coada goala."
        //                                format: "Procesat: [id] data tip: suma RON"
        //   PUSH id suma data tip      → addFirst  (niciun output)
        //   POP                        → removeFirst sau "Coada goala."
        //                                format: "Extras: [id] data tip: suma RON"
        //   REMOVE_DEBIT               → Iterator.remove() pe toate DEBIT
        //                                afișează "Eliminat N tranzactii DEBIT."
        //   REMOVE_BELOW threshold     → Iterator.remove() pe suma < threshold
        //                                afișează "Eliminat N tranzactii sub threshold RON."
        //   PRINT                      → afișează toate, câte una pe linie
        //   SIZE                       → "Dimensiune coada: N"
        //
        // Format linie tranzacție: [id] data tip: suma RON
        //   Ex: [1] 2024-01-10 CREDIT: 500.00 RON

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] elemente = line.split(" ");
            if (elemente[0].equals("ENQUEUE"))
            {
                int id =  Integer.parseInt(elemente[1]);
                double suma =  Double.parseDouble(elemente[2]);
                String data = elemente[3];
                String tip =  elemente[4];
                QueueTranzactii.addLast(new Tranzactie(id, suma, data, tip));
            }
            else if  (elemente[0].equals("DEQUEUE"))//presupun ca trebuie removelast nu removefirst
            {
                if (QueueTranzactii.isEmpty())
                    System.out.println("Coada goala.");
                else
                {
                    System.out.println("Extras: " + QueueTranzactii.getLast());
                    QueueTranzactii.removeLast();
                }
            }
            else if (elemente[0].equals("PUSH"))
            {
                int id =  Integer.parseInt(elemente[1]);
                double suma =  Double.parseDouble(elemente[2]);
                String data = elemente[3];
                String tip =  elemente[4];
                QueueTranzactii.addFirst(new Tranzactie(id, suma, data, tip));
            }
            else if (elemente[0].equals("POP"))
            {
                if (QueueTranzactii.isEmpty())
                    System.out.println("Coada goala.");
                else
                {
                    System.out.println("Procesat: " + QueueTranzactii.getFirst());
                    QueueTranzactii.removeFirst();
                }
            }
            else if (elemente[0].equals("REMOVE_DEBIT"))
            {
                Iterator<Tranzactie> it = QueueTranzactii.iterator();
                int contor = 0;
                while (it.hasNext()) {
                    Tranzactie tranzactie = it.next();
                    if (tranzactie.getTip() == TipTranzactie.DEBIT) {
                        it.remove();
                        ++contor;
                    }
                }
                System.out.println("Eliminat " + contor + " tranzactii DEBIT.");
            }
            else if (elemente[0].equals("REMOVE_BELOW"))
            {
                double threshhold =  Double.parseDouble(elemente[1]);
                Iterator<Tranzactie> it = QueueTranzactii.iterator();
                int contor = 0;
                while (it.hasNext()) {
                    Tranzactie tranzactie = it.next();
                    if (tranzactie.getSuma() <  threshhold) {
                        it.remove();
                        ++contor;
                    }
                }
                System.out.printf("Eliminat %d tranzactii sub %.2f RON.%n", contor, threshhold);
            }
            else if (elemente[0].equals("PRINT"))
            {
                for (Tranzactie tranzactie : QueueTranzactii) {
                    System.out.println(tranzactie);
                }
            }
            else if (elemente[0].equals("SIZE"))
            {
                System.out.println("Dimensiune coada: " + QueueTranzactii.size());
            }
        }
    }
}
