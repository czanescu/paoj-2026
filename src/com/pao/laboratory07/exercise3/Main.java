package com.pao.laboratory07.exercise3;

import java.util.*;

import com.pao.laboratory07.exercise3.comenzi.Comanda;
import com.pao.laboratory07.exercise3.comenzi.ComandaGratuita;
import com.pao.laboratory07.exercise3.comenzi.ComandaRedusa;
import com.pao.laboratory07.exercise3.comenzi.ComandaStandard;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine().trim());
        List<Comanda> comenzi = new ArrayList<>();
        int nrStandard = 0, nrDiscounted = 0, nrGift = 0;
        double sumaStandard = 0, sumaDiscounted = 0;
        for (int i = 0; i < n; i++) {
            String line = sc.nextLine().trim();
            String[] tokens = line.split(" ");
            if (tokens[0].equals("STANDARD")) {
                String nume = tokens[1];
                double pret = Double.parseDouble(tokens[2]);
                String client = tokens[3];
                Comanda c = new ComandaStandard(nume, pret, client);
                comenzi.add(c);
                nrStandard++;
                sumaStandard += c.pretFinal();
            } else if (tokens[0].equals("DISCOUNTED")) {
                String nume = tokens[1];
                double pret = Double.parseDouble(tokens[2]);
                int discount = Integer.parseInt(tokens[3]);
                String client = tokens[4];
                Comanda c = new ComandaRedusa(nume, pret, discount, client);
                comenzi.add(c);
                nrDiscounted++;
                sumaDiscounted += c.pretFinal();
            } else if (tokens[0].equals("GIFT")) {
                String nume = tokens[1];
                String client = tokens[2];
                Comanda c = new ComandaGratuita(nume, client);
                comenzi.add(c);
                nrGift++;
            }
        }
        String line = sc.nextLine().trim();
        String[] tokens = line.split(" ");
        while (!tokens[0].equals("QUIT")){
            switch (tokens[0]) {
                case "STATS":
                    System.out.println("STATS: ");
                    System.out.printf("STANDARD: medie = %.2f lei \n", sumaStandard / nrStandard);
                    System.out.printf("DISCOUNTED: medie = %.2f lei \n", sumaDiscounted / nrDiscounted);
                    System.out.println("GIFT: medie = 0.00 lei\n");
                    break;
                case "FILTER":
                    int filtru = Integer.parseInt(tokens[1]);
                    System.out.println("FILTER: ");
                    for (Comanda c : comenzi) {
                        if (c.pretFinal() >= filtru)
                            System.out.println(c.descriere());
                    }
                    break;
                case "SORT":
                    List<Comanda> comenziSortate = new ArrayList<>(comenzi);
                    comenziSortate.sort(Comparator.comparing(Comanda::getClient).thenComparingDouble(Comanda::pretFinal));
                    System.out.println("SORT: ");
                    for (Comanda c : comenziSortate) {
                        System.out.println(c.descriere());
                    }
                    break;
                case "SPECIAL":
                    System.out.println("SPECIAL (discount > 15%): ");
                    for (Comanda c : comenzi) {
                        if (c.getDiscountProcent() > 15)
                            System.out.println(c.descriere());
                    }
            }
            line = sc.nextLine().trim();
            tokens = line.split(" ");
        }
        System.out.println("Programul se închide");
    }
}