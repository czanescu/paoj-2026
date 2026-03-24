package com.pao.laboratory05.angajati;

import java.util.Arrays;

public class AngajatService {
    private Angajat[] angajati;

    private AngajatService() {
        this.angajati = new Angajat[0];
    }
    private static class Holder {
        private static final AngajatService INSTANCE = new AngajatService();
    }
    public static AngajatService getInstance() {
        return AngajatService.Holder.INSTANCE;
    }

    void addAngajat(Angajat angajat) {
        Angajat[] newArr = new Angajat[this.angajati.length + 1];
        System.arraycopy(this.angajati, 0, newArr, 0, this.angajati.length);
        newArr[this.angajati.length] = angajat;
        this.angajati = newArr;
        System.out.println("A fost adăugat angajatul: " + angajat.getNume() + ", din departamentul: " + angajat.getDepartament() + ", cu salariul: " + angajat.getSalariu() + " lei.");
    }

    void printAll() {
        for (Angajat angajat : angajati) {
            System.out.println(angajat);
        }
    }

    void listBySalary() {
        Angajat[] angajatiSortati= this.angajati.clone();
        Arrays.sort(angajatiSortati);
        for (Angajat angajat : angajatiSortati) {System.out.println(angajat);}
    }

    void findByDepartments(String numeDept) {
        int counter = 0;
        for (Angajat angajat : angajati) {
            if (angajat.getDepartament().nume().equalsIgnoreCase(numeDept)) {
                System.out.println(angajat);
                counter++;
            }
        }
        if (counter == 0) {
            System.out.println("Nici-un angajat in departamentul: " + numeDept);
        }
    }
}
