package com.pao.laboratory05.audit;

import com.pao.laboratory05.audit.Angajat;

import java.time.LocalDateTime;
import java.util.Arrays;

public class AngajatService {
    private Angajat[] angajati;
    private AuditEntry[] auditLog;

    private AngajatService() {
        this.angajati = new Angajat[0];
        this.auditLog = new AuditEntry[0];
    }
    private static class Holder {
        private static final AngajatService INSTANCE = new AngajatService();
    }
    public static AngajatService getInstance() {
        return Holder.INSTANCE;
    }

    private void logAction(String action, String target){
        AuditEntry Entry = new AuditEntry(action, target, LocalDateTime.now().toString());
        AuditEntry[] newArr = new AuditEntry[this.auditLog.length + 1];
        System.arraycopy(this.auditLog, 0, newArr, 0, this.auditLog.length);
        newArr[this.auditLog.length] = Entry;
        this.auditLog = newArr;
    }

    public void addAngajat(Angajat angajat) {
        Angajat[] newArr = new Angajat[this.angajati.length + 1];
        System.arraycopy(this.angajati, 0, newArr, 0, this.angajati.length);
        newArr[this.angajati.length] = angajat;
        this.angajati = newArr;
        logAction("ADD", angajat.getNume());
        System.out.println("A fost adăugat angajatul: " + angajat.getNume() + ", din departamentul: " + angajat.getDepartament() + ", cu salariul: " + angajat.getSalariu() + " lei.");
    }

    public void printAll() {
        for (Angajat angajat : angajati) {
            System.out.println(angajat);
        }
    }

    public void listBySalary() {
        Angajat[] angajatiSortati= new Angajat[this.angajati.length];
        System.arraycopy(this.angajati, 0, angajatiSortati, 0, this.angajati.length);
        Arrays.sort(angajatiSortati);
        for (Angajat angajat : angajatiSortati) {System.out.println(angajat);}
    }

    public void findByDepartments(String numeDept) {
        logAction("FIND_BY_DEPT", numeDept);
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

    public void printAuditLog() {
        for (AuditEntry entry : auditLog) {
            System.out.println(entry);
        }
    }
}
