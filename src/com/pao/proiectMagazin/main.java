package com.pao.proiectMagazin;

import com.pao.proiectMagazin.Modele.*;
import com.pao.proiectMagazin.Servicii.ContorCodInventar;
import com.pao.proiectMagazin.Servicii.UserAccountService;

import java.io.*;
import java.util.*;

public class main {
    static Scanner sc = new Scanner(System.in);

    public static void asteptareTasta()
    {
        System.out.println("Apăsați orice tastă pentru a reveni la meniul principal");
        sc.nextLine();
    }

    public static void main(String[] args) {
        System.out.println("Proiect gestionare stoc magazin\n");
        File bd = new File("dateSecurizate(plain text)");
        List<Utilizator> utilizatori = new ArrayList<>();
        List<Furnizor> furnizori = new ArrayList<>();
        Map<Integer, Produs> produse = new HashMap<>();
        Angajat admin = new Angajat("admin", "Zănescu", "Cristian", 5, "5050227111111", "Strada Anonimă nr 3", "0752111111", "cristian.zanescu@gmail.com", "adminadmin", "27.02.2005", "14.04.2026", 0);
        utilizatori.add(admin);
        try (Scanner parser = new Scanner(bd)) {
            int nrConturi = parser.nextInt();
            System.out.println("Conturi încărcate: " + nrConturi);
            int nrFurnizori = parser.nextInt();
            System.out.println("Furnizori încărcați: " + nrFurnizori);
            int nrProduse = parser.nextInt();
            System.out.println("Produse încărcate: " + nrProduse);
            int ultimulCodProdus = parser.nextInt();
            System.out.println("Ultimul cod de inventar: " + ultimulCodProdus);
            ContorCodInventar.initialize(ultimulCodProdus);
            int nrVanzari = parser.nextInt();
            System.out.println("Vânzări încărcate: " + nrVanzari);
            parser.nextLine();
            while (nrConturi > 0) {
                nrConturi--;
                String username = parser.nextLine();
                String nume = parser.nextLine();
                String prenume = parser.nextLine();
                int salariu = parser.nextInt();
                parser.nextLine();
                String cnp = parser.nextLine();
                String adresa = parser.nextLine();
                String telefon = parser.nextLine();
                String email = parser.nextLine();
                String parola = parser.nextLine();
                String dataNasterii = parser.nextLine();
                String dataAngajare = parser.nextLine();
                String dataConcediere = parser.nextLine();
                int uid = parser.nextInt();
                parser.nextLine();
                String tip = parser.nextLine();
                Utilizator utilizator;
                if (tip.equals("manager")) {
                    utilizator = new Manager(username, nume, prenume, salariu, cnp, adresa, telefon, email, parola, dataNasterii, dataAngajare, uid);
                }
                else
                {
                    utilizator = new Angajat(username, nume, prenume, salariu, cnp, adresa, telefon, email, parola, dataNasterii, dataAngajare, uid);
                }
                if (!Objects.equals(dataConcediere, " ")) utilizator.concediere(dataConcediere);
                utilizatori.add(utilizator);
            }
            while (nrFurnizori > 0) {
                nrFurnizori--;
                String nume = parser.nextLine();
                String adresa = parser.nextLine();
                String telefon = parser.nextLine();
                String email = parser.nextLine();
                String cui = parser.nextLine();
                Furnizor furnizor = new Furnizor(nume, adresa, telefon, email, cui);
                furnizori.add(furnizor);
            }
            while (nrProduse > 0) {
                nrProduse--;
                int codInventar = parser.nextInt();
                parser.nextLine();
                String nume = parser.nextLine();
                int pretCumparare = parser.nextInt();
                int pretVanzare = parser.nextInt();
                parser.nextLine();
                String categorie = parser.nextLine();
                String cuiFurnizor = parser.nextLine();
                int stocMinim = parser.nextInt();
                int stoc = parser.nextInt();
                int procentReducere = parser.nextInt();
                int nrSpec = parser.nextInt();
                parser.nextLine();
                List<String> specificatii = new ArrayList<>();
                while (nrSpec > 0) {
                    nrSpec--;
                    String spec = parser.nextLine();
                    specificatii.add(spec);
                }
                Produs produs = new Produs(codInventar, nume, pretCumparare, pretVanzare, categorie, cuiFurnizor, stocMinim, stoc, procentReducere, specificatii);
                produse.put(codInventar, produs);
            }
        } catch (FileNotFoundException e) {
            try {
                if (bd.createNewFile()) {
                    System.out.println("Fișier creat: " + bd.getName());
                } else {
                    System.out.println("S-a încercat crearea pentru că nu a fost găsit, dar nu s-a reușit pentru că defapt există \uD83E\uDD37");
                }
            } catch (IOException er) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        UserAccountService accountService = UserAccountService.getInstance();;
        do {
            System.out.print("username:");
            String username = sc.nextLine();
            System.out.print("parola:");
            String parola = sc.nextLine();
            boolean wrongPass = false;
            boolean deactivated = false;
            for (Utilizator utilizator : utilizatori) {
                if (Objects.equals(utilizator.getUsername(), username)) {
                    if (Objects.equals(utilizator.getParola(), parola)) {
                        if (utilizator.getDataConcediere()!=null)
                        {
                            System.out.println("Contul este dezactivat");
                            deactivated = true;
                            break;
                        }
                        System.out.println("\nBine ai venit " + utilizator.getNume() + " " + utilizator.getPrenume()+"\n");
                        accountService.login(utilizator);
                        break;
                    } else {
                        System.out.println("Parola Gresita");
                        wrongPass = true;
                    }
                }
            }
            if (!accountService.isLoggedIn() && !wrongPass && !deactivated) System.out.println("User-ul " + username + " nu exista!");
        } while (!accountService.isLoggedIn());
        boolean isRunning = true;
        while (isRunning)
        {
            System.out.println("Opțiuni:\n");
            System.out.println("1. Adăugare furnizor");
            System.out.println("2. Modificare furnizor");
            System.out.println("3. Adăugare produs");
            System.out.println("4. Modificare produs");
            System.out.println("5. Recepție marfă");
            System.out.println("6. Modificare stoc");
            System.out.println("7. Vânzare produs");
            System.out.println("8. Afișare furnizori");
            System.out.println("9. Afișare produse");
            System.out.println("10.Afișare stoc critic");
            System.out.println("11.Căutare produs");
            System.out.println("12.Aplică reducere");
            System.out.println("13.Raport vânzări");
            System.out.println("14.Ștergere produs");
            System.out.println("15.Ștergere furnizor");
            System.out.println("16.Creare cont angajat");
            System.out.println("17.Detalii angajați");
            System.out.println("18.Șterge angajat");
            System.out.println("0. Save & Exit");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 0:
                {
                    isRunning = false;
                    try (PrintWriter pw = new PrintWriter(new FileWriter(bd))) {
                        int nrUseri = utilizatori.size() - 1;
                        int nrFurnizori = furnizori.size();
                        int nrProduse = produse.size();
                        int UltimulCodInventar = ContorCodInventar.getInstance().getCodInventar();
                        int nrVanzari = 0;
                        pw.println(nrUseri);
                        pw.println(nrFurnizori);
                        pw.println(nrProduse);
                        pw.println(UltimulCodInventar);
                        pw.println(nrVanzari);
                        for (Utilizator utilizator : utilizatori) {
                            if (utilizator.getUsername().equals("admin")) continue;
                            pw.println(utilizator.getUsername());
                            pw.println(utilizator.getNume());
                            pw.println(utilizator.getPrenume());
                            pw.println(utilizator.getSalariu());
                            pw.println(utilizator.getCnp());
                            pw.println(utilizator.getAdresa());
                            pw.println(utilizator.getTelefon());
                            pw.println(utilizator.getEmail());
                            pw.println(utilizator.getParola());
                            pw.println(utilizator.getDataNasterii());
                            pw.println(utilizator.getDataAngajare());
                            pw.println(utilizator.getDataConcediere());
                            pw.println(utilizator.getUid());
                            if (utilizator.getRol().equals("manager")) pw.println("manager");
                            else pw.println("angajat");
                        }
                        for (Furnizor furnizor : furnizori) {
                            pw.println(furnizor.getNume());
                            pw.println(furnizor.getAdresa());
                            pw.println(furnizor.getTelefon());
                            pw.println(furnizor.getEmail());
                            pw.println(furnizor.getCui());
                        }
                        for (Produs produs: produse.values()) {
                            pw.println(produs.getCodInventar());
                            pw.println(produs.getNume());
                            pw.println(produs.getPretCumparare());
                            pw.println(produs.getPretVanzare());
                            pw.println(produs.getCategorie());
                            pw.println(produs.getCuiFurnizor());
                            pw.println(produs.getStocMinim());
                            pw.println(produs.getStoc());
                            pw.println(produs.getProcentReducere());
                            pw.println(produs.getSpecificatii().size());
                            for (String spec : produs.getSpecificatii()) {
                                pw.println(spec);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Salvare reușită!");
                    break;
                }
                case 1:
                {
                    System.out.println("Adăugare furnizor\n");
                    System.out.print("Nume:");
                    String nume = sc.nextLine();
                    System.out.print("Adresa:");
                    String adresa = sc.nextLine();
                    System.out.print("Telefon:");
                    String telefon = sc.nextLine();
                    System.out.print("Email:");
                    String email = sc.nextLine();
                    System.out.print("CUI:");
                    String cui = sc.nextLine();
                    boolean gasit = true;
                    while (gasit)
                    {
                        gasit = false;
                        for (Furnizor furnizor : furnizori) {
                            if (furnizor.getCui().equals(cui)) {
                                gasit = true;
                                System.out.println("CUI-ul este deja folosit");
                                System.out.print("CUI:");
                                cui = sc.nextLine();
                                break;
                            }
                        }
                    }
                    Furnizor furnizor = new Furnizor(nume, adresa, telefon, email, cui);
                    furnizori.add(furnizor);
                    break;
                }
                case 2:
                {
                    System.out.println("Modificare furnizor\n");
                    System.out.print("Introdu CUI-ul pentru modificare:");
                    String cui = sc.nextLine();
                    boolean gasit = false;
                    for (Furnizor furnizor : furnizori) {
                        if (furnizor.getCui().equals(cui)) {
                            gasit = true;
                            System.out.println("Furnizor găsit");
                            System.out.println("În câmpurile următoare lasă gol pentru a nu schimba:");
                            System.out.println("Nume nou:");
                            String input = sc.nextLine();
                            if (input.isEmpty()) input = furnizor.getNume();
                            furnizor.setNume(input);
                            System.out.println("Adresa nouă:");
                            input = sc.nextLine();
                            if (input.isEmpty()) input = furnizor.getAdresa();
                            furnizor.setAdresa(input);
                            System.out.println("Telefon nou:");
                            input = sc.nextLine();
                            if (input.isEmpty()) input = furnizor.getTelefon();
                            furnizor.setTelefon(input);
                            System.out.println("Email nou:");
                            input = sc.nextLine();
                            if (input.isEmpty()) input = furnizor.getEmail();
                            furnizor.setEmail(input);
                            System.out.println("Noile date din furnizor: " + furnizor);
                            break;
                        }
                    }
                    if (!gasit)
                    {
                        System.out.println("Nu s-a găsit furnizorul");
                    }
                    asteptareTasta();
                    break;
                }
                case 3:
                {
                    System.out.println("Adăugare produs\n");
                    System.out.println("Nume:");
                    String nume = sc.nextLine();
                    System.out.println("Preț cumpărare:");
                    int pretCumparare = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Preț vânzare:");
                    int pretVanzare = sc.nextInt();
                    sc.nextLine();
                    if (pretCumparare > pretVanzare)
                        System.out.println("ATENTIE: COSTUL DE VÂNZARE ESTE MAI MIC DECÂT CEL DE CUMPĂRARE!");
                    System.out.println("Categorie:");
                    String categorie = sc.nextLine();
                    String cuiFurnizor;
                    boolean exista = false, iesire = false;
                    do{
                        System.out.println("CUI furnizor:");
                        cuiFurnizor = sc.nextLine();
                        if (cuiFurnizor.equals("end"))
                        {
                            iesire = true;
                            break;
                        }
                        for (Furnizor furnizor : furnizori) {
                            if (furnizor.getCui().equals(cuiFurnizor)) {
                                exista = true;
                                break;
                            }
                        }
                        if (!exista)
                        {
                            System.out.println("CUI-ul introdus nu este asociat cu nici-un furnizor (scrie end pentru a iesi din comandă).");
                        }
                    }while (!exista);
                    if (iesire) break;
                    System.out.println("Stoc minim:");
                    int stocMinim = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Număr specificații:");
                    int numSpecificatii = sc.nextInt();
                    sc.nextLine();
                    List<String> specificatii = new ArrayList<>();
                    for (int i = 0; i < numSpecificatii; i++) {
                        specificatii.add(sc.nextLine());
                    }
                    int codInventar = ContorCodInventar.getInstance().getCodInventar();
                    Produs produs = new Produs(codInventar, nume, pretCumparare, pretVanzare, categorie, cuiFurnizor, stocMinim, 0, specificatii);
                    produse.put(codInventar, produs);
                    System.out.println("S-a introdus produsul: ");
                    System.out.println(produs);
                    asteptareTasta();
                    break;
                }
                case 4:
                {
                    System.out.println("Modificare produs\n");
                    System.out.println("Introdu codul produsului: ");
                    int codInventar = sc.nextInt();
                    sc.nextLine();
                    if (!produse.containsKey(codInventar))
                    {
                        System.out.println("Codul introdus nu este asociat cu niciun produs");
                        asteptareTasta();
                        break;
                    }
                    System.out.println("Produs găsit");
                    System.out.println("În câmpurile următoare lasă gol pentru a nu schimba:");
                    System.out.println("Nume vechi: " + produse.getOrDefault(codInventar, null).getNume() + " Nume nou:");
                    String input = sc.nextLine();
                    if (input.isEmpty()) input = produse.getOrDefault(codInventar, null).getNume();
                    produse.getOrDefault(codInventar, null).setNume(input);
                    System.out.println("Preț cumpărare vechi: " + produse.getOrDefault(codInventar, null).getPretCumparare() + " Preț cumpărare nou:");
                    input = sc.nextLine();
                    if (input.isEmpty()) input = String.format("%s",produse.getOrDefault(codInventar, null).getPretCumparare());
                    produse.getOrDefault(codInventar, null).setPretCumparare(Integer.parseInt(input));
                    System.out.println("Preț vânzare vechi: " + produse.getOrDefault(codInventar, null).getPretVanzare() + " Preț vânzare nou:");
                    input = sc.nextLine();
                    if (input.isEmpty()) input = String.format("%s",produse.getOrDefault(codInventar, null).getPretVanzare());
                    if (Integer.parseInt(input) < produse.getOrDefault(codInventar, null).getPretCumparare())
                        System.out.println("ATENȚIE: Prețul de vânzare este mai mic decât cel de cumpărare!");
                    produse.getOrDefault(codInventar, null).setPretVanzare(Integer.parseInt(input));
                    System.out.println("Categorie veche: " + produse.getOrDefault(codInventar, null).getCategorie() + " Categorie nouă:");
                    input = sc.nextLine();
                    if (input.isEmpty()) input = produse.getOrDefault(codInventar, null).getCategorie();
                    produse.getOrDefault(codInventar, null).setCategorie(input);
                    System.out.println("CUI furnizor vechi: " + produse.getOrDefault(codInventar, null).getCuiFurnizor() + " CUI furnizor nou:");
                    input = sc.nextLine();
                    if (input.isEmpty()) input = produse.getOrDefault(codInventar, null).getCuiFurnizor();
                    produse.getOrDefault(codInventar, null).setCuiFurnizor(input);
                    System.out.println("Stoc minim vechi: " + produse.getOrDefault(codInventar, null).getStocMinim()  + " Stoc minim nou:");
                    input = sc.nextLine();
                    if (input.isEmpty()) input = String.format("%s",produse.getOrDefault(codInventar, null).getStocMinim());
                    produse.getOrDefault(codInventar, null).setStocMinim(Integer.parseInt(input));
                    System.out.println("Specificații vechi:");
                    for (String spec : produse.getOrDefault(codInventar, null).getSpecificatii())
                    {
                        System.out.println(spec);
                    }
                    System.out.println("Doriți să schimbați specificațiile? (y/n)");
                    String opt = sc.nextLine();
                    List<String> specificatii = new ArrayList<>();
                    if (opt.equals("y"))
                    {
                        System.out.println("Număr specificații: ");
                        int numSpecificatii = sc.nextInt();
                        sc.nextLine();
                        for (int i = 0; i < numSpecificatii; i++) {
                            specificatii.add(sc.nextLine());
                        }
                        produse.getOrDefault(codInventar, null).setSpecificatii(specificatii);
                    }
                    else specificatii = produse.getOrDefault(codInventar, null).getSpecificatii();
                    produse.getOrDefault(codInventar, null).setSpecificatii(specificatii);
                    System.out.println("Noile date din produs: " + produse.getOrDefault(codInventar, null));
                    asteptareTasta();
                    break;
                }
                case 5:
                {
                    System.out.println("Recepție marfă\n");
                    System.out.println("Introdu codul produsului: ");
                    int codInventar = sc.nextInt();
                    sc.nextLine();
                    if (!produse.containsKey(codInventar))
                    {
                        System.out.println("Codul introdus nu este asociat cu niciun produs. Apasă orice tastă pentru a reveni la meniul principal");
                        sc.nextLine();
                        break;
                    }
                    System.out.println("Număr produse noi:");
                    int numProduse = sc.nextInt();
                    sc.nextLine();
                    if (numProduse <= 0)
                    {
                        System.out.println("Stocul primit nu poate fi negativ. Apasă orice tastă pentru a reveni la meniul principal");
                        sc.nextLine();
                        break;
                    }
                    produse.getOrDefault(codInventar, null).addStoc(numProduse);
                    System.out.println("Stoc adăugat. Stoc nou: " + produse.getOrDefault(codInventar, null).getStoc());
                    asteptareTasta();
                    break;
                }
                case 6:
                {
                    System.out.println("Modificare stoc\n");
                    System.out.print("Introdu codul produsului: ");
                    int codInventar = sc.nextInt();
                    sc.nextLine();
                    if (!produse.containsKey(codInventar))
                    {
                        System.out.println("Codul introdus nu este asociat cu niciun produs. Apasă orice tastă pentru a reveni la meniul principal");
                        sc.nextLine();
                        break;
                    }
                    System.out.println("Stocul actual al produsului " + produse.getOrDefault(codInventar, null).getNume() + " este: " + produse.getOrDefault(codInventar, null).getStoc());
                    System.out.print("Diferență (scrie număr negativ dacă trebuie scăzut inventarul, și pozitiv dacă trebuie să crească: ");
                    int numProduse = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Introdu motivul pentru modificarea stocului: ");
                    String motiv = sc.nextLine();
                    ///TO DO RECORD CU MOTIV MODIFICARE
                    produse.getOrDefault(codInventar, null).addStoc(numProduse);
                    System.out.println("Stocul după modificare al produsului " + produse.getOrDefault(codInventar, null).getNume() + " este: " + produse.getOrDefault(codInventar, null).getStoc());
                    asteptareTasta();
                    break;
                }
                case 7:
                {
                    System.out.println("Vânzare produse\n");
                    System.out.print("Introdu codul produsului: ");
                    int codInventar = sc.nextInt();
                    sc.nextLine();
                    if (!produse.containsKey(codInventar))
                    {
                        System.out.println("Codul introdus nu este asociat cu niciun produs. Apasă orice tastă pentru a reveni la meniul principal");
                        sc.nextLine();
                        break;
                    }
                    System.out.println("Stocul actual al produsului " + produse.getOrDefault(codInventar, null).getNume() + " este: " + produse.getOrDefault(codInventar, null).getStoc());
                    System.out.println("Câte produse s-au vândut?");
                    int nrProd = sc.nextInt();
                    sc.nextLine();
                    if (nrProd <=0)
                    {
                        System.out.println("Nu pot vine număr negativ de produse. Apasă orice tastă pentru a reveni la meniul principal");
                        sc.nextLine();
                        break;
                    }
                    if (nrProd > produse.getOrDefault(codInventar, null).getStoc())
                    {
                        System.out.println("Stocul este insuficient pentru vânzare. Apasă orice tastă pentru a reveni la meniul principal");
                        sc.nextLine();
                        break;
                    }
                    produse.getOrDefault(codInventar, null).vanzare(nrProd);
                    System.out.println("Stocul după vânzarea produsului " + produse.getOrDefault(codInventar, null).getNume() + " este: " + produse.getOrDefault(codInventar, null).getStoc());
                    asteptareTasta();
                    break;
                }
                case 8:
                {
                    System.out.println("Afișare furnizori\n");
                    for (Furnizor furnizor : furnizori) {
                        System.out.println(furnizor.toString());
                    }
                    asteptareTasta();
                    break;
                }
                case 9:
                {
                    System.out.println("Afișare produse\n");
                    /// TO DO SORTAREA
                    System.out.println("Introdu categorie produs (sau keyword-ul 'tot' pentru toate produsele): ");
                    String categorie = sc.nextLine();
                    if (categorie.equals("tot"))
                    {
                        for (Produs produs : produse.values()) {
                            System.out.println(produs);
                        }
                        asteptareTasta();
                        break;
                    }
                    boolean gasit = false;
                    for (Produs produs : produse.values()) {
                        if (produs.getCategorie().equals(categorie)) {
                            gasit = true;
                            System.out.println(produs);
                        }
                    }
                    if (!gasit) System.out.println("Nu s-a găsit nici-un produs din categoria " + categorie + ".");
                    asteptareTasta();
                    break;
                }
                case 10:
                {
                    System.out.println("Afișare stoc critic\n");
                    for (Produs produs : produse.values())
                    {
                        if (produs.getStocMinim() > produs.getStoc())
                        {
                            System.out.println(produs);
                        }
                    }
                    asteptareTasta();
                    break;
                }
                case 11:
                {
                    System.out.println("Căutare produs\n");
                    System.out.print("Introdu codul produsului: ");
                    int codInventar = sc.nextInt();
                    sc.nextLine();
                    if (!produse.containsKey(codInventar))
                    {
                        System.out.println("Codul introdus nu este asociat cu niciun produs. Apasă orice tastă pentru a reveni la meniul principal");
                        sc.nextLine();
                        break;
                    }
                    System.out.println(produse.getOrDefault(codInventar, null));
                    asteptareTasta();
                    break;
                }
                case 12:
                {
                    System.out.println("Aplicare reducere\n");
                    System.out.print("Introdu codul produsului: ");
                    int codInventar = sc.nextInt();
                    sc.nextLine();
                    if (!produse.containsKey(codInventar))
                    {
                        System.out.println("Codul introdus nu este asociat cu niciun produs. Apasă orice tastă pentru a reveni la meniul principal");
                        sc.nextLine();
                        break;
                    }
                    System.out.println("Reducere actuală: " + produse.getOrDefault(codInventar, null).getProcentReducere() + "%.");
                    System.out.print("Reducere noua: ");
                    int reducere = sc.nextInt();
                    sc.nextLine();
                    if (reducere > 100)
                    {
                        System.out.println("Reducerea nu poate fi mai mare ca 100! Apasă orice tastă pentru a reveni la meniul principal");
                        sc.nextLine();
                        break;
                    }
                    if (reducere < 0)
                    {
                        System.out.println("Reducerea nu poate fi negativă! Apasă orice tastă pentru a reveni la meniul principal");
                        sc.nextLine();
                        break;
                    }
                    produse.getOrDefault(codInventar, null).setProcentReducere(reducere);
                    System.out.println("S-a aplicat reducerea de " + reducere + "% asupra produsului cu codul " + codInventar + ".");
                    asteptareTasta();
                    break;
                }
                case 13:
                {
                    System.out.println("Raport vânzări\n");
                    ///TO DO
                    break;
                }
                case 14:
                {
                    System.out.println("Ștergere produs\n");
                    System.out.println("Introdu codul produsului: ");
                    int codInventar = sc.nextInt();
                    sc.nextLine();
                    if (!produse.containsKey(codInventar))
                    {
                        System.out.println("Codul introdus nu este asociat cu niciun produs");
                        asteptareTasta();
                        break;
                    }
                    Produs produs = produse.getOrDefault(codInventar, null);
                    if (produs.getStoc() > 0)
                    {
                        System.out.println("Pentru a elimina un produs stocul acestuia trebuie să fie 0!");
                        asteptareTasta();
                        break;
                    }
                    System.out.println("Confirmă ștergerea produsului (tastează 'yes') " + produs);
                    String confirmare = sc.nextLine();
                    if (confirmare.equals("yes"))
                    {
                        produse.remove(codInventar);
                        System.out.println("Produs șters.");
                    }
                    asteptareTasta();
                    break;
                }
                case 15:
                {
                    System.out.println("Ștergere furnizor\n");
                    System.out.println("Introdu CUI-ul furnizorului: ");
                    String cui = sc.nextLine();
                    boolean gasit = false;
                    int poz = 0;
                    for (int i = 0; i < furnizori.size(); i++) {
                        if (furnizori.get(i).getCui().equals(cui))
                        {
                            gasit = true;
                            poz = i;
                            break;
                        }
                    }
                    if (!gasit)
                    {
                        System.out.println("CUI-ul introdus nu este asociat cu nici-un furnizor");
                        asteptareTasta();
                        break;
                    }
                    gasit = false;
                    for (Produs produs : produse.values()) {
                        if (produs.getCuiFurnizor().equals(cui))
                        {
                            gasit = true;
                            break;
                        }
                    }
                    if (gasit)
                    {
                        System.out.println("Există produse ce provin de la acest furnizor. Pentru a-l putea șterge, șterge mai întâi produsele respective.");
                        asteptareTasta();
                        break;
                    }
                    System.out.println("Se va șterge furnizorul " + furnizori.get(poz));
                    System.out.println("Scrie 'yes' pentru a confirma!");
                    String opt = sc.nextLine();
                    if (opt.equals("yes"))
                    {
                        furnizori.remove(poz);
                        System.out.println("Ștergere reușită!");
                        asteptareTasta();
                        break;
                    }
                    System.out.println("Nu s-a efectuat ștergerea. Apăsați orice tastă pentru a reveni la meniul principal.");
                    sc.nextLine();
                    break;
                }
                    case 16:
                    {
                        System.out.println("Creare cont angajat\n");
                        System.out.println("Angajatul are deja cont? (y/n)");
                        String opt = sc.nextLine();
                        if (opt.equals("y"))
                        {
                            System.out.println("Introdu uid-ul angajatului: ");
                            int uid = sc.nextInt();
                            sc.nextLine();
                            boolean gasit = false;
                            for (int i = 0; i < utilizatori.size(); i++)
                            {
                                if (utilizatori.get(i).getUid() == uid)
                                {
                                    gasit = true;
                                    System.out.println("S-a găsit angajatul " + utilizatori.get(i).toStringDetaliat());
                                    System.out.println("Doriți să schimbați altceva înafară de data de angajare? (y/n)");
                                    String opt2 = sc.nextLine();
                                    if (opt2.equals("y"))
                                    {
                                        System.out.println("Lasă gol dacă nu dorești modificare:");
                                        System.out.print("Nume vechi:" + utilizatori.get(i).getNume() + " Nume nou:");
                                        String nume = sc.nextLine();
                                        if (nume.isEmpty()) nume = utilizatori.get(i).getNume();
                                        System.out.print("Prenume vechi:" + utilizatori.get(i).getPrenume() + " Prenume nou:");
                                        String prenume = sc.nextLine();
                                        if (prenume.isEmpty()) prenume = utilizatori.get(i).getPrenume();
                                        System.out.print("Salariu vechi:" + utilizatori.get(i).getSalariu() + " Salariu nou:");
                                        String salariuString = sc.nextLine();
                                        if (salariuString.isEmpty()) salariuString = utilizatori.get(i).getSalariu() + "";
                                        int salariu = Integer.parseInt(salariuString);
                                        System.out.print("Adresa veche:" + utilizatori.get(i).getAdresa() + " Adresa nouă:");
                                        String adresa = sc.nextLine();
                                        if (adresa.isEmpty()) adresa = utilizatori.get(i).getAdresa();
                                        System.out.print("Telefon vechi:" + utilizatori.get(i).getTelefon() + " Telefon nou:");
                                        String telefon = sc.nextLine();
                                        if (telefon.isEmpty()) telefon = utilizatori.get(i).getTelefon();
                                        System.out.print("Email vechi:" + utilizatori.get(i).getEmail() + " Email nou:");
                                        String email = sc.nextLine();
                                        if (email.isEmpty()) email = utilizatori.get(i).getEmail();
                                        System.out.print("Parolă veche:" + utilizatori.get(i).getParola() + " Parolă nouă:");
                                        String parola = sc.nextLine();
                                        if (parola.isEmpty()) parola = utilizatori.get(i).getParola();
                                        System.out.print("Data angajare:");
                                        String dataAngajare = sc.nextLine();
                                        System.out.print("Rol(angajat / manager):");
                                        String rol = sc.nextLine();
                                        if (rol.equals("angajat") && utilizatori.get(i).getRol().equals("Angajat") || rol.equals("manager") && utilizatori.get(i).getRol().equals("Manager"))
                                        {
                                            utilizatori.get(i).reangajare(dataAngajare, nume, prenume, salariu, adresa, telefon, email, parola);
                                        }
                                        else if (rol.equals("manager"))
                                        {
                                            utilizatori.set(i, new Manager(utilizatori.get(i).getUsername(), nume, prenume, salariu, utilizatori.get(i).getCnp(), adresa, telefon, email, parola, utilizatori.get(i).getDataNasterii(), dataAngajare, utilizatori.get(i).getUid()));
                                        }
                                        else if (rol.equals("angajat"))
                                        {
                                            utilizatori.set(i, new Angajat(utilizatori.get(i).getUsername(), nume, prenume, salariu, utilizatori.get(i).getCnp(), adresa, telefon, email, parola, utilizatori.get(i).getDataNasterii(), dataAngajare, utilizatori.get(i).getUid()));
                                        }
                                        else System.out.println("Rol invalid");
                                    }
                                    else
                                    {
                                        System.out.print("Data angajare:");
                                        String dataAngajare = sc.nextLine();
                                        utilizatori.get(i).reangajare(dataAngajare);
                                    }
                                    break;
                                }
                            }
                            if (!gasit)
                            {
                                System.out.println("Angajatul nu a fost gasit");
                            }
                        }
                        else{
                            System.out.print("Username:");
                            String username = sc.nextLine();
                            System.out.print("Nume:");
                            String nume = sc.nextLine();
                            System.out.print("Prenume:");
                            String prenume = sc.nextLine();
                            System.out.print("Salariu:");
                            int salariu = sc.nextInt();
                            sc.nextLine();
                            System.out.print("CNP:");
                            String cnp = sc.nextLine();
                            System.out.print("Adresa:");
                            String adresa = sc.nextLine();
                            System.out.print("Telefon:");
                            String telefon = sc.nextLine();
                            System.out.print("Email:");
                            String email = sc.nextLine();
                            System.out.print("Parola:");
                            String parola = sc.nextLine();
                            System.out.print("Data nasterii:");
                            String dataNasterii = sc.nextLine();
                            System.out.print("Data angajare:");
                            String dataAngajare = sc.nextLine();
                            System.out.print("Rol(angajat / manager):");
                            String rol = sc.nextLine();
                            if (rol.equals("angajat")) {
                                Utilizator utilizator = new Angajat(username, nume, prenume, salariu, cnp, adresa, telefon, email, parola, dataNasterii, dataAngajare, utilizatori.size());
                                utilizatori.add(utilizator);
                            }
                            else if (rol.equals("manager")) {
                                Utilizator utilizator = new Manager(username, nume, prenume, salariu, cnp, adresa, telefon, email, parola, dataNasterii, dataAngajare, utilizatori.size());
                                utilizatori.add(utilizator);
                                break;
                            }
                            else
                            {
                                System.out.println("Rol invalid");
                                break;
                            }
                            System.out.println("Cont creat cu succes");
                        }
                        break;
                    }
                case 17:
                {
                    System.out.println("Detalii angajat\n");
                    System.out.println("Afișare toți angajații(și cei demisionați) - t\nAfișare angajați - a\nAfișare doar un angajat - u\n");
                    String opt = sc.nextLine();
                    if (opt.equals("t"))
                        for (Utilizator utilizator : utilizatori) {
                            System.out.println(utilizator.toStringCompact());
                        }
                    else if (opt.equals("a"))
                        for (Utilizator utilizator : utilizatori) {
                            if (utilizator.eAngajat())
                                System.out.println(utilizator.toStringCompact());
                        }
                    else
                    {
                        System.out.println("Introdceți uid-ul sau username-ul angajatului:");
                        String input = sc.nextLine();
                        boolean eNumar = true;
                        try{
                            Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            eNumar = false;
                        }
                        for (Utilizator utilizator : utilizatori) {
                            if (eNumar)
                            {
                                if (utilizator.getUid() == Integer.parseInt(input)) {
                                    System.out.println(utilizator.toStringDetaliat());
                                }
                            }
                            if (utilizator.getUsername().equals(input)) {
                                System.out.println(utilizator.toStringDetaliat());
                            }
                        }
                    }
                    asteptareTasta();
                    break;
                }
                case 18:
                {
                    System.out.println("Stergere angajat\n");
                    System.out.println("Introduceți data concedierii:");
                    String dataConcediere = sc.nextLine();
                    System.out.println("Introduceți uid-ul angajatului:");
                    int input = sc.nextInt();
                    sc.nextLine();
                    boolean gasit = false;
                    for (Utilizator utilizator : utilizatori) {

                        if (utilizator.getUid() == input) {
                            utilizator.concediere(dataConcediere);
                            gasit = true;
                            break;
                        }
                    }
                    if (gasit) System.out.println("Angajatul a fost concediat cu succes");
                    else System.out.println("Angajatul nu a fost gasit");
                    break;
                }
            }
        }
    }
}
