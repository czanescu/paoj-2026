package com.pao.laboratory06.exercise3;

import com.pao.laboratory06.exercise3.Indivizi.Inginer;
import com.pao.laboratory06.exercise3.Indivizi.PersoanaJuridica;

import java.util.Arrays;

public class Main {
    public static void main() {
        Inginer[] ingineri = {
                new Inginer("Popescu", "Andrei", "0711111111", 5000, 8000),
                new Inginer("Ionescu", "Maria", "0722222222", 7000, 6000),
                new Inginer("Avram", "Paul", "0733333333", 6000, 4000)
        };

        // comparatorul default
        Arrays.sort(ingineri);
        System.out.println("Sortare naturală după nume:");
        for (Inginer inginer : ingineri) {
            System.out.println(inginer.getNume() + " - " + inginer.getSalariu());
        }

        // comparatorul custom
        Arrays.sort(ingineri, new ComparatorInginerSalariu());
        System.out.println("\nSortare descrescătoare după salariu:");
        for (Inginer inginer : ingineri) {
            System.out.println(inginer.getNume() + " - " + inginer.getSalariu());
        }

        // test plată online
        Inginer plataOnline = new Inginer("Georgescu", "Dan", "0744444444", 8000, 10000);
        plataOnline.autentificare("dan.georgescu", "parolaunicaaluidangeorgescu");
        System.out.println("\nSold înainte de plata online: " + plataOnline.consultareSold());
        System.out.println("Plata de 1000: " + plataOnline.efectuarePlata(1000));
        System.out.println("Sold după plată: " + plataOnline.consultareSold());

        // test plată online cu trimitere SMS
        PersoanaJuridica clientSMS = new PersoanaJuridica("SC", "Tech SRL", "0755555555", 5000);
        clientSMS.autentificare("SCTECHSRL", "e_secret");
        System.out.println("\nSold înainte de plata online: " + clientSMS.consultareSold());
        System.out.println("Plata de 1000: " + clientSMS.efectuarePlata(1000));
        System.out.println("Sold după plată: " + clientSMS.consultareSold());
        System.out.println("Lista de mesaje: ");
        clientSMS.printSMS();

        // test comportament metodă SMS pentru mesaj gol
        System.out.println("Încercare trimitere SMS invalid (mesaj gol): " + clientSMS.trimiteSMS(""));

        // test trimitere SMS cu telefon null
        PersoanaJuridica faraTelefon = new PersoanaJuridica("SC", "NoPhone SRL", null, 4000);
        try {
            faraTelefon.trimiteSMS("Mesajul ar trebui să intre în try");
        } catch (Exception e) {
            System.out.println("\nEroare SMS fără telefon: " + e.getMessage());
        }

        // test trimitere SMS dintr-o clasă care nu poate
        try {
            new com.pao.laboratory06.exercise3.Indivizi.Angajat().trimiteSMS("Încerc să trimit sms dintr-un angajat, angajații nu pot face asta");
        } catch (UnsupportedOperationException e) {
            System.out.println("\nEroare capabilitate SMS lipsă: " + e.getMessage());
        }

        // test autentificare cu user null
        try {
            plataOnline.autentificare(null, "parola");
        } catch (IllegalArgumentException e) {
            System.out.println("\nEroare autentificare: " + e.getMessage());
        }



        // Nu am idee la ce aș fi putut folosi constantele, aici mai jos cum le-aș fi apelat dacă le-aș fi folosit
        System.out.println("\nTVA = " + ConstanteFinanciare.TVA.getValoare());
    }
}
