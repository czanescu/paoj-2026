package com.pao.laboratory05.audit;

import com.pao.laboratory05.audit.Angajat;
import com.pao.laboratory05.audit.AngajatService;
import com.pao.laboratory05.audit.Departament;

import java.util.Scanner;

/**
 * Exercise 4 (Bonus) — Audit Log
 *
 * Cerințele complete se află în:
 *   src/com/pao/laboratory05/Readme.md  →  secțiunea "Exercise 4 (Bonus) — Audit"
 *
 * Extinde soluția de la Exercise 3 cu un sistem de audit bazat pe record.
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
            System.out.println("4. Afișează audit log");
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
                case "4":
                    System.out.print("Audit log: \n");
                    service.printAuditLog();
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
