package com.pao.laboratory11.exercise2;

import com.pao.laboratory11.exercise1.Transaction;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        int n, q;
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        sc.nextLine();

        LinkedList<Transaction> tranzactii = new LinkedList<>();
        for (int i = 0; i < n; ++i) {
            String line = sc.nextLine();
            String[] linearr = line.split(" ");
            int id       = Integer.parseInt(linearr[0]);
            double suma  = Double.parseDouble(linearr[1]);
            String data  = linearr[2];
            String country = linearr[3];
            String channel = linearr[4];
            String account = linearr[5];
            tranzactii.addLast(new Transaction(id, suma, data, country, channel, account));
        }
        Map<String, List<Transaction>> byMonth = tranzactii.stream().collect(Collectors.groupingBy(t -> t.getDate().substring(0, 7)));
        Map<String, List<Transaction>> byAccount = tranzactii.stream().collect(Collectors.groupingBy(Transaction::getAccount));
        Map<String, Long> byChannel = tranzactii.stream().collect(Collectors.groupingBy(Transaction::getChannel, Collectors.counting()));

        q = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < q; ++i) {
            String line = sc.nextLine();
            String[] linearr = line.split(" ");

            if (linearr[0].equals("REPORT_MONTH")) {
                String luna = linearr[1];
                List<Transaction> result = byMonth.getOrDefault(luna, Collections.emptyList());
                double total = result.stream().mapToDouble(Transaction::getAmount).sum();
                System.out.printf("MONTH %s total=%.2f count=%d%n", luna, total, result.size());

            }
            else if (linearr[0].equals("REPORT_ACCOUNT")) {
                String account = linearr[1];
                List<Transaction> result = byAccount.getOrDefault(account, Collections.emptyList());
                double total = result.stream().mapToDouble(Transaction::getAmount).sum();
                System.out.printf("ACCOUNT %s total=%.2f count=%d%n", account, total, result.size());

            }
            else if (linearr[0].equals("TOP_CHANNELS")) {
                int k = Integer.parseInt(linearr[1]);
                List<Map.Entry<String, Long>> top = byChannel.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).limit(k).toList();
                for (Map.Entry<String, Long> e : top) System.out.println(e.getKey() + " " + e.getValue());

            }
            else throw new IllegalArgumentException("Invalid input: " + linearr[0]);
        }
    }
}