package com.pao.laboratory11.exercise1;

import java.util.*;
import java.util.function.Predicate;

import static java.lang.Math.floor;
import static java.lang.Math.min;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        LinkedList<Transaction> Tranzactii = new LinkedList();
        LinkedList<Transaction> Flagged = new LinkedList<>();
        List<Transaction> secret3rdList = new ArrayList<>();
        Predicate<Transaction> isHighAmount = t -> t.getAmount() >= 5000;
        List<String> SCORE_COUNTRIES = Arrays.asList("NG", "RU", "UA", "CN", "BR", "KP");
        List<String> SUSPICIOUS_CHANNELS = Arrays.asList("CRYPTO");
        Predicate<Transaction> isSuspiciousChannel = t -> SUSPICIOUS_CHANNELS.contains(t.getChannel());
        Predicate<Transaction> flaggedRule = isHighAmount.or(isSuspiciousChannel);
        for(int i = 0; i < n; ++i)
        {
            String line = sc.nextLine();
            String[] lineArr = line.split(" ");
            int id =  Integer.parseInt(lineArr[0]);
            double amount = Double.parseDouble(lineArr[1]);
            String date = lineArr[2];
            String country = lineArr[3];
            String channel =  lineArr[4];
            Transaction transaction = new Transaction(id, amount, date, country, channel);
            if (flaggedRule.test(transaction)) transaction.setFlag();
            int amountScore = Math.min(55, (int)(amount / 500) * 5);
            int countryScore = SCORE_COUNTRIES.contains(country) ? 10 : 0;
            int channelScore = switch (channel) {
                case "WEB"    -> 45;
                case "APP",
                     "MOBILE" -> 40;
                case "CRYPTO" -> 50;
                case "ATM"    -> 20;
                case "POS"    -> 20;
                default       -> 0;
            };
            int scorFinal = amountScore + countryScore + channelScore;
            transaction.setScore(scorFinal);
            Tranzactii.add(transaction);
            secret3rdList.add(transaction);
            if (transaction.getVerdict() == Verdict.FLAG) Flagged.add(transaction);
        }
        Flagged.sort(Comparator.comparingDouble(Transaction::getScore).reversed()
                    .thenComparing(Comparator.comparingDouble(Transaction::getAmount).reversed())
                    .thenComparing(Transaction::getDate)
                    .thenComparing(Transaction::getId));

        secret3rdList.sort(Comparator.comparingDouble(Transaction::getScore).reversed()
                    .thenComparing(Comparator.comparingDouble(Transaction::getAmount).reversed())
                    .thenComparing(Transaction::getDate)
                    .thenComparing(Transaction::getId));

        int Q = sc.nextInt();
        sc.nextLine();
        for(int i = 0; i < Q; ++i)
        {
            String line = sc.nextLine();
            String[] lineArr = line.split(" ");
            String command = lineArr[0];
            if (command.equals("CHECK"))
            {
                boolean found = false;
                int nr = Integer.parseInt(lineArr[1]);
                for (Transaction transaction : Tranzactii) {
                    if (transaction.getId() == nr) {
                        System.out.println("CHECK " + nr + " => " + transaction.getVerdict() + " score=" + transaction.getScore());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("CHECK " + nr + " => NOT_FOUND");
                }
            }
            else if (command.equals("LIST_FLAGGED"))
            {
                if (Flagged.isEmpty()) {
                    System.out.println("NONE");
                } else {
                    for (Transaction t : Flagged)
                        System.out.printf("[%d] %s score=%d\n", t.getId(), t.getVerdict(), t.getScore());
                }

            }
            else if (command.equals("TOP_RISK"))
            {
                int k = Integer.parseInt(lineArr[1]);
                if (k > n) k = n;
                for (int j = 0; j < k; ++j)
                {
                    System.out.printf("[%d] %s score=%d\n", secret3rdList.get(j).getId(), secret3rdList.get(j).getVerdict(), secret3rdList.get(j).getScore());
                }
            }
            else System.out.print("ERR UNKNOWN_COMMAND");
        }
    }
}
