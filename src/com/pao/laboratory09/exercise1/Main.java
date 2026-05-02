package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";

    public static void main(String[] args) throws Exception {
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data contSursa contDestinatie tip)
        Scanner scanner = new Scanner(System.in);
        int N, id;
        double suma;
        String data, contSursa, contDestinatie, tip, input;
        N = scanner.nextInt();
        scanner.nextLine();
        List<Tranzactie> tranzactii = new ArrayList<>();
        for (int i = 0; i < N; ++i)
        {
            input = scanner.nextLine();
            String[] inputs = input.split(" ");
            id = Integer.parseInt(inputs[0]);
            suma = Double.parseDouble(inputs[1]);
            data = inputs[2];
            contSursa = inputs[3];
            contDestinatie = inputs[4];
            tip = inputs[5];
            try{
                Tranzactie tranzactie = new Tranzactie(id, suma, data, contSursa, contDestinatie, tip);
                tranzactii.add(tranzactie);
            }catch(IllegalArgumentException e){
                System.out.println(e);
                return;
            }
        }
        // 2. Setează câmpul note = "procesat" pe fiecare tranzacție înainte de serializare
        for (int i = 0; i < N; ++i)
        {
            tranzactii.get(i).setNote();
        }
        // 3. Serializează lista de tranzacții în OUTPUT_FILE cu ObjectOutputStream (try-with-resources)
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(OUTPUT_FILE)))
        {
            oos.writeObject(tranzactii);
        } catch (IOException e) {
            System.err.println("Eroare la scrierea in fisier: " + e.getMessage());
        }
        // 4. Deserializează lista din OUTPUT_FILE cu ObjectInputStream (try-with-resources)
        List<Tranzactie> listaDeserializata = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(OUTPUT_FILE)))
        {
            listaDeserializata = (List<Tranzactie>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.err.println("Eroare: Fișierul " + OUTPUT_FILE + " nu a fost găsit!");
        } catch (IOException e) {
            System.err.println("Eroare la citirea din fișier: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Eroare: Clasa Tranzactie nu a fost găsită: " + e.getMessage());
        }

        // 5. Procesează comenzile din stdin până la EOF:
        //    - LIST          → afișează toate tranzacțiile, câte una pe linie
        //    - FILTER yyyy-MM → afișează tranzacțiile cu data care începe cu yyyy-MM
        //                       sau "Niciun rezultat." dacă nu există
        //    - NOTE id        → afișează "NOTE[id]: <valoarea câmpului note>"
        //                       sau "NOTE[id]: not found" dacă id-ul nu există
        //
        // Format linie tranzacție:
        //   [id] data tip: suma RON | contSursa -> contDestinatie
        //   Ex: [1] 2024-01-15 CREDIT: 1500.00 RON | RO01SRC1 -> RO01DST1

        while (scanner.hasNextLine()) {
            String linie = scanner.nextLine().trim();
            if (linie.isEmpty()) continue;

            String[] parti = linie.split(" ");
            String comanda = parti[0].toUpperCase();

            switch (comanda) {
                case "LIST":
                    if (listaDeserializata.isEmpty()) {
                        System.out.println("Lista este goală.");
                    } else {
                        listaDeserializata.forEach(System.out::println);
                    }
                    break;

                case "FILTER":
                    if (parti.length < 2) {
                        System.out.println("Eroare: Specificați data în format yyyy-MM.");
                        break;
                    }
                    String prefix = parti[1];
                    List<Tranzactie> filtrate = listaDeserializata.stream().filter(t -> t.getData() != null && t.getData().startsWith(prefix)).toList();

                    if (filtrate.isEmpty()) {
                        System.out.println("Niciun rezultat.");
                    } else {
                        filtrate.forEach(System.out::println);
                    }
                    break;

                case "NOTE":
                    if (parti.length < 2) {
                        System.out.println("Eroare: Specificați ID-ul tranzacției.");
                        break;
                    }
                    try {
                        int idCautat = Integer.parseInt(parti[1]);
                        listaDeserializata.stream()
                                .filter(t -> t.getId() == idCautat)
                                .findFirst()
                                .ifPresentOrElse(
                                        t -> System.out.println("NOTE[" + idCautat + "]: " + t.note),
                                        () -> System.out.println("NOTE[" + idCautat + "]: not found")
                                );
                    } catch (NumberFormatException e) {
                        System.out.println("Eroare: ID-ul trebuie să fie un număr.");
                    }
                    break;

                default:
                    System.out.println("Comandă necunoscută: " + comanda);
            }
        }
    }
}
