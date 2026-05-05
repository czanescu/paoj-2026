package com.pao.laboratory10.exercise2;

import com.pao.laboratory10.exercise1.Tranzactie;
import com.pao.laboratory10.exercise1.TipTranzactie;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data tip) — pot exista duplicate de id
        //    Stochează-le toate într-un ArrayList<Tranzactie> (cu duplicate, ordine inserare)
        //
        // 2. Procesează comenzile din stdin până la EOF:
        //
        //   UNIQUE_IDS      → LinkedHashSet<Integer> cu id-urile în ordinea primei apariții
        //                     afișează: "IDs unice (N): [1, 2, 3, ...]"
        //
        //   MONTHLY_REPORT  → TreeMap<String, ...> grupat pe yyyy-MM (substring 0-7 din data)
        //                     pentru fiecare lună, sumele CREDIT și DEBIT
        //                     format: "yyyy-MM: CREDIT X.XX RON, DEBIT Y.YY RON"
        //
        //   TOP n           → primele n tranzacții după suma descrescătoare (nu modifică lista)
        //                     afișează "Top n:" urmat de n linii
        //
        //   SORT_ASC        → Collections.sort cu suma crescătoare; afișează lista sortată
        //   SORT_DESC       → Collections.sort cu suma descrescătoare; afișează lista sortată
        //   REVERSE         → Collections.reverse; afișează lista
        //   MIN_MAX         → Collections.min/max după suma
        //                     "MIN: [id] data tip: suma RON"
        //                     "MAX: [id] data tip: suma RON"
        //
        //   CME_DEMO        → încearcă for(t : lista) lista.remove(t) în try-catch
        //                     afișează "ConcurrentModificationException prins: modificare in iteratie detectata."
        //
        // Format linie tranzacție: [id] data tip: suma RON
        //   Ex: [1] 2024-01-15 CREDIT: 1500.00 RON
        ArrayList<Tranzactie> tranzactii = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int N = Integer.parseInt(sc.nextLine().trim());
        for (int i = 0; i < N; i++) {
            String line = sc.nextLine();
            String[] elemente = line.split(" ");
            int id = Integer.parseInt(elemente[0]);
            double suma = Double.parseDouble(elemente[1]);
            String data = elemente[2];
            String tip = elemente[3];
            tranzactii.add(new Tranzactie(id, suma, data, tip));
        }

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] elemente = line.split(" ");

            if (elemente[0].equals("UNIQUE_IDS")) {
                LinkedHashSet<Integer> uniqueIds = new LinkedHashSet<>();
                for (Tranzactie t : tranzactii)
                    uniqueIds.add(t.getId());
                System.out.println("IDs unice (" + uniqueIds.size() + "): " + uniqueIds);

            }
            else if (elemente[0].equals("MONTHLY_REPORT")) {
                TreeMap<String, double[]> report = new TreeMap<>();
                for (Tranzactie t : tranzactii) {
                    String luna = t.getData().substring(0, 7);
                    report.putIfAbsent(luna, new double[]{0.0, 0.0});
                    if (t.getTip() == TipTranzactie.CREDIT)
                        report.get(luna)[0] += t.getSuma();
                    else
                        report.get(luna)[1] += t.getSuma();
                }
                for (Map.Entry<String, double[]> entry : report.entrySet()) {
                    System.out.printf("%s: CREDIT %.2f RON, DEBIT %.2f RON%n", entry.getKey(), entry.getValue()[0], entry.getValue()[1]);
                }

            }
            else if (elemente[0].equals("TOP")) {
                int n = Integer.parseInt(elemente[1]);
                ArrayList<Tranzactie> sorted = new ArrayList<>(tranzactii);
                sorted.sort((a, b) -> Double.compare(b.getSuma(), a.getSuma()));
                System.out.println("Top " + n + ":");
                for (int i = 0; i < Math.min(n, sorted.size()); i++) {
                    System.out.println(sorted.get(i));
                }

            }
            else if (elemente[0].equals("SORT_ASC")) {
                ArrayList<Tranzactie> sorted = new ArrayList<>(tranzactii);
                sorted.sort(Comparator.comparingDouble(Tranzactie::getSuma));
                for (Tranzactie t : sorted) System.out.println(t);

            }
            else if (elemente[0].equals("SORT_DESC")) {
                ArrayList<Tranzactie> sorted = new ArrayList<>(tranzactii);
                sorted.sort(Comparator.comparingDouble(Tranzactie::getSuma).reversed());
                for (Tranzactie t : sorted) System.out.println(t);

            }
            else if (elemente[0].equals("REVERSE")) {
                ArrayList<Tranzactie> reversed = new ArrayList<>(tranzactii);
                Collections.reverse(reversed);
                System.out.println(reversed);

            }
            else if (elemente[0].equals("MIN_MAX")) {
                Tranzactie min = Collections.min(tranzactii, Comparator.comparingDouble(Tranzactie::getSuma));
                Tranzactie max = Collections.max(tranzactii, Comparator.comparingDouble(Tranzactie::getSuma));
                System.out.println("MIN: " + min);
                System.out.println("MAX: " + max);

            }
            else if (elemente[0].equals("CME_DEMO")) {
                try {
                    for (Tranzactie t : tranzactii) tranzactii.remove(t);
                } catch (ConcurrentModificationException e) {
                    System.out.println("ConcurrentModificationException prins: modificare in iteratie detectata.");
                }
            }
        }
    }
}