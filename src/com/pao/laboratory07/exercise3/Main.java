package com.pao.laboratory07.exercise3;

import java.util.*;
import java.util.stream.Collectors;

import com.pao.laboratory07.exercise3.comenzi.Comanda;
import com.pao.laboratory07.exercise3.comenzi.ComandaGratuita;
import com.pao.laboratory07.exercise3.comenzi.ComandaRedusa;
import com.pao.laboratory07.exercise3.comenzi.ComandaStandard;
import com.pao.laboratory07.exercise3.exceptii.InputInvalidException;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n;
        try {
            n = Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            throw new InputInvalidException("Nu a fost introdus un numar n de comenzi");
        }
        List<Comanda> comenzi = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) throw new InputInvalidException("Comanda este goala");
            String[] tokens = line.split(" ");
            if (tokens[0].equals("STANDARD")) {
                if (tokens.length < 4)
                    throw new InputInvalidException("Au fost introduse prea puține argumente");
                String nume = tokens[1];
                double pret = Double.parseDouble(tokens[2]);
                String client = tokens[3];
                Comanda c = new ComandaStandard(nume, pret, client);
                comenzi.add(c);
            } else if (tokens[0].equals("DISCOUNTED")) {
                if (tokens.length < 5)
                    throw new InputInvalidException("Au fost introduse prea puține argumente");
                String nume = tokens[1];
                double pret = Double.parseDouble(tokens[2]);
                int discount = Integer.parseInt(tokens[3]);
                String client = tokens[4];
                Comanda c = new ComandaRedusa(nume, pret, discount, client);
                comenzi.add(c);
            } else if (tokens[0].equals("GIFT")) {
                if (tokens.length < 3)
                    throw new InputInvalidException("Au fost introduse prea puține argumente");
                String nume = tokens[1];
                String client = tokens[2];
                Comanda c = new ComandaGratuita(nume, client);
                comenzi.add(c);
            }
        }
        System.out.println();
        for (Comanda c : comenzi) {
            System.out.println(c.descriere());
        }
        System.out.println();
        Map<String, Double> medii = comenzi.stream().collect(Collectors.groupingBy(c -> c.getTipComanda(), Collectors.averagingDouble(Comanda::pretFinal)));
        String line = sc.nextLine().trim();
        String[] tokens = line.split(" ");
        while (!tokens[0].equals("QUIT")) {
            switch (tokens[0]) {
                case "STATS":
                    System.out.println("STATS: ");
                    System.out.printf("STANDARD: medie = %.2f lei \n", medii.getOrDefault("STANDARD", 0.0));
                    System.out.printf("DISCOUNTED: medie = %.2f lei \n", medii.getOrDefault("DISCOUNTED", 0.0));
                    System.out.println("GIFT: medie = 0.00 lei\n");
                    break;
                case "FILTER":
                    int filtru = Integer.parseInt(tokens[1]);
                    System.out.println("FILTER (pret >= " + filtru + " lei: ");
                    List<String> comenziFiltrate = comenzi.stream().filter(c -> c.pretFinal() >= filtru).map(Comanda::descriere).toList();
                    for (String descriere : comenziFiltrate) {
                        System.out.println(descriere);
                    }
                    System.out.println();
                    break;
                case "SORT":
                    List<Comanda> comenziSortate = new ArrayList<>(comenzi);
                    comenziSortate.sort(Comparator.comparing(Comanda::getClient).thenComparingDouble(Comanda::pretFinal));
                    System.out.println("SORT: ");
                    for (Comanda c : comenziSortate) {
                        System.out.println(c.descriere());
                    }
                    System.out.println();
                    break;
                case "SPECIAL":
                    System.out.println("SPECIAL (discount > 15%): ");
                    for (Comanda c : comenzi) {
                        if (c.getDiscountProcent() > 15)
                            System.out.println(c.descriere());
                    }
                    System.out.println();
                    break;
                default:
                    throw new InputInvalidException("Comanda " + tokens[0] + " nu este recunoscută!");
            }
            line = sc.nextLine().trim();
            tokens = line.split(" ");
        }
        System.out.println("Programul se închide");
    }
}