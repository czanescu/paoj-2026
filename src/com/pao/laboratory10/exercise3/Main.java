package com.pao.laboratory10.exercise3;

import com.pao.laboratory10.exercise1.Tranzactie;
import com.pao.laboratory10.exercise1.TipTranzactie;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        LinkedList<Tranzactie> lista =  new LinkedList<>();
        lista.addLast(new Tranzactie(1,200,"2024-01-15", "CREDIT", "Cont A"));
        lista.addLast(new Tranzactie(2,500,"2024-01-16", "DEBIT",  "Cont B"));
        lista.addLast(new Tranzactie(3,750,"2024-02-26", "DEBIT", "Cont A"));
        lista.addLast(new Tranzactie(4,100,"2024-02-30", "CREDIT",  "Cont C"));
        lista.addLast(new Tranzactie(5,600,"2024-03-10", "DEBIT", "Cont C"));
        lista.addLast(new Tranzactie(6,300,"2024-03-11", "CREDIT", "Cont D"));
        lista.addLast(new Tranzactie(7,350,"2024-03-11", "DEBIT",  "Cont A"));
        lista.addLast(new Tranzactie(8,1000,"2024-04-01", "DEBIT", "Cont C"));
        lista.addLast(new Tranzactie(9,300,"2024-04-01", "CREDIT", "Cont B"));
        lista.addLast(new Tranzactie(10,300,"2024-04-11", "CREDIT",  "Cont C"));
        lista.addLast(new Tranzactie(11,300,"2024-04-15", "CREDIT", "Cont D"));

        System.out.println("Tranzactii");
        for (Tranzactie tranzactie : lista) {
            System.out.println(tranzactie);
        }
        System.out.println("Filtru după tip == credit");
        Stream<Tranzactie> stream = lista.stream().filter(t -> t.getTip() == TipTranzactie.CREDIT);
        stream.forEach(System.out::println);

        System.out.println("MapToDouble");
        double suma = lista.stream().mapToDouble(Tranzactie::getSuma).sum();
        System.out.printf("Total procesat: %.2f RON\n", suma);

        System.out.println("Valoarea intr-o luna");
        Map<String, Double> sumaPerLuna = lista.stream().collect(Collectors.groupingBy(
                t -> t.getData().substring(0, 7),
                Collectors.summingDouble(Tranzactie::getSuma)));

        sumaPerLuna.forEach((luna, valoare) ->
                System.out.printf("Luna %s: %.2f RON%n", luna, valoare));

        System.out.println("Top 3 tranzactii:");
        Stream<Tranzactie> top3tranzactii = lista.stream().sorted(Comparator.comparingDouble(Tranzactie::getSuma).reversed()).limit(3);
        top3tranzactii.forEach(System.out::println);

        System.out.println("Conturi sursa unice:");
        List<String> Conturi = lista.stream().map(Tranzactie::getContSursa).distinct().collect(Collectors.toList());
        System.out.println(Conturi);

        System.out.println("Average pe mapToDouble");
        double average = lista.stream().mapToDouble(Tranzactie::getSuma).average().getAsDouble();
        System.out.printf("Suma medie: %.2f RON\n", average);

        System.out.println("Valoarea intr-o luna format extras");
        Map<String, List<Tranzactie>> grupePeLuna = lista.stream().collect(Collectors.groupingBy(
                t -> t.getData().substring(0, 7)));

        grupePeLuna.forEach((luna, tranzactii) -> {
            double total = tranzactii.stream().mapToDouble(Tranzactie::getSuma).sum();
            System.out.printf("EXTRAS DE CONT - %s: %d tranzactii, total: %.2f RON%n",
                    luna, tranzactii.size(), total);
        });
    }
}
