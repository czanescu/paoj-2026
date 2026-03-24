package com.pao.laboratory05.angajati;

import java.util.Scanner;

/**
 * Exercise 3 — Angajați
 *
 * Cerințele complete se află în:
 *   src/com/pao/laboratory05/Readme.md  →  secțiunea "Exercise 3 — Angajați"
 *
 * Creează fișierele de la zero în acest pachet, apoi rulează Main.java
 * pentru a verifica output-ul așteptat din Readme.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        AngajatService service = AngajatService.getInstance();
        while (running) {
            System.out.println("\n===== Gestionare Angajați =====");
            System.out.println("1. Adaugă angajat");
            System.out.println("2. Listare după salariu");
            System.out.println("3. Caută după departament");
            System.out.println("0. Ieșire");
            System.out.print("Opțiune: ");
            String option = scanner.nextLine().trim();
            switch (option) {
                case "1":
                    System.out.print("Nume: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Salariu: ");
                    double salary = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Nume departament: ");
                    String deptName = scanner.nextLine().trim();
                    System.out.print("Locatie departament: ");
                    String deptLocation = scanner.nextLine().trim();
                    Departament dept = new Departament(deptName, deptLocation);
                    service.addAngajat(new Angajat(name, dept, salary));
                    System.out.println("Angajat adăugat.");
                    break;

                case "2":
                    System.out.print("Listare după salariu: \n");
                    service.listBySalary();
                    break;

                case "3":
                    System.out.print("Nume departament: ");
                    String deptNameSrc = scanner.nextLine().trim();
                    service.findByDepartments(deptNameSrc);
                    break;
                case "0":
                    running = false;
                    System.out.println("La revedere!");
                    break;

                default:
                    System.out.println("Opțiune invalidă.");
            }
        }
    }
}
