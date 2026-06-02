package com.pao.proiectMagazin;

import com.pao.proiectMagazin.modele.*;
import com.pao.proiectMagazin.servicii.*;
import com.pao.proiectMagazin.exceptii.*;
import com.pao.proiectMagazin.repository.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void asteptareTasta() {
        System.out.println("Apăsați orice tastă pentru a reveni la meniul principal");
        sc.nextLine();
    }

    public static void main(String[] args) {

        System.out.println("Proiect gestionare stoc magazin\n");
        List<Utilizator> utilizatori = new ArrayList<>();
        ProdusService produsService = ProdusService.getInstance();
        FurnizorService furnizorService = FurnizorService.getInstance();

        FurnizorRepository furnizorRepository = new FurnizorRepository();
        ProdusRepository produsRepository = new ProdusRepository();
        UtilizatorRepository utilizatorRepository = new UtilizatorRepository();
        VanzareRepository vanzareRepository = new VanzareRepository();
        ModificareStocRepository modificareStocRepository = new ModificareStocRepository();

        try {
            utilizatori.addAll(utilizatorRepository.findAll());
            furnizorService.incarcaFurnizori(furnizorRepository.findAll());
            for (Produs produs : produsRepository.findAll()) {
                produsService.adaugaProdus(produs);
            }
            for (VanzareRecord vanzareRecord : vanzareRepository.findAll()) {
                produsService.getRaportVanzari().adaugaVanzare(vanzareRecord);
            }
            for (ModificareStocRecord modificareStocRecord : modificareStocRepository.findAll()) {
                produsService.addModificareStoc(modificareStocRecord);
            }
                int maxCodProdus = produsService.listeazaToate().stream().mapToInt(Produs::getCodInventar).max().orElse(-1);
                int urmatorulCodProdus = maxCodProdus < 0 ? 0 : maxCodProdus + 1;
                ContorCodInventar.initialize(urmatorulCodProdus);

            System.out.println("Conturi încărcate: " + utilizatori.size());
            System.out.println("Furnizori încărcați: " + furnizorService.listeazaToate().size());
            System.out.println("Produse încărcate: " + produsService.listeazaToate().size());
            System.out.println("Ultimul cod de inventar: " + urmatorulCodProdus);
            System.out.println("Vânzări încărcate: " + produsService.getRaportVanzari().getVanzari().size());
            System.out.println("Modificări stoc încărcate: " + produsService.getNrModificariStoc());
        } catch (RuntimeException e) {
            System.out.println("Eroare la încărcarea din baza de date: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        boolean adminExists = false;
        for (Utilizator utilizator : utilizatori) {
            if ("admin".equalsIgnoreCase(utilizator.getUsername())) {
                adminExists = true;
                break;
            }
        }
        if (!adminExists) {
            Angajat admin = new Angajat("admin", "admin", "admin", 0, "0000000000000", "admin", "0000000000", "admin", "adminadmin", "admin", "admin", utilizatori.size());
            utilizatori.add(admin);
        }
        UserAccountService accountService = UserAccountService.getInstance();
        AuditService auditService = AuditService.getInstance();
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
                        if (utilizator.getDataConcediere() != null) {
                            System.out.println("Contul este dezactivat");
                            deactivated = true;
                            break;
                        }
                        System.out.println("\nBine ai venit " + utilizator.getNume() + " " + utilizator.getPrenume() + "\n");
                        accountService.login(utilizator);
                        auditService.logAction("login");
                        break;
                    } else {
                        System.out.println("Parola Gresita");
                        wrongPass = true;
                    }
                }
            }
            if (!accountService.isLoggedIn() && !wrongPass && !deactivated)
                System.out.println("User-ul " + username + " nu exista!");
        } while (!accountService.isLoggedIn());
        boolean isRunning = true;
        while (isRunning) {
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
            System.out.println("14.Rapoarte diverse");
            System.out.println("15.Ștergere produs");
            System.out.println("16.Ștergere furnizor");
            if (accountService.getLoggedInUser().getRol().equals("Manager") || accountService.getLoggedInUser().getUsername().equals("admin")) {
                System.out.println("17.Creare cont angajat");
                System.out.println("18.Detalii angajați");
                System.out.println("19.Modificare angajat");
                System.out.println("20.Șterge angajat");
            }
            System.out.println("0. Exit");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 0: {
                    auditService.logAction("exit");
                    isRunning = false;
                    break;
                }
                case 1: {
                    auditService.logAction("adauga_furnizor");
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
                    while (gasit) {
                        gasit = furnizorService.existaCui(cui);
                        if (gasit) {
                            System.out.println("CUI-ul este deja folosit");
                            System.out.print("CUI:");
                            cui = sc.nextLine();
                        }
                    }
                    Furnizor furnizor = new Furnizor(nume, adresa, telefon, email, cui);
                    furnizorService.adaugaFurnizor(furnizor);
                    try {
                        furnizorRepository.save(furnizor);
                    } catch (RuntimeException e) {
                        System.out.println("Salvare furnizor eșuată: " + e.getMessage());
                    }
                    break;
                }
                case 2: {
                    auditService.logAction("modifica_furnizor");
                    System.out.println("Modificare furnizor\n");
                    System.out.print("Introdu CUI-ul pentru modificare:");
                    String cui = sc.nextLine();
                    try {
                        Furnizor furnizor = furnizorService.cautaDupaCui(cui);
                        System.out.println("Furnizor găsit");
                        System.out.println("În câmpurile următoare lasă gol pentru a nu schimba:");
                        System.out.print("Nume vechi: " + furnizor.getNume() + ", Nume nou:");
                        String input = sc.nextLine();
                        if (input.isEmpty()) input = furnizor.getNume();
                        furnizor.setNume(input);
                        System.out.print("Adresa veche: " + furnizor.getAdresa() + "Adresa nouă:");
                        input = sc.nextLine();
                        if (input.isEmpty()) input = furnizor.getAdresa();
                        furnizor.setAdresa(input);
                        System.out.print("Telefon vechi: " + furnizor.getTelefon() + "Telefon nou:");
                        input = sc.nextLine();
                        if (input.isEmpty()) input = furnizor.getTelefon();
                        furnizor.setTelefon(input);
                        System.out.print("Email vechi: " + furnizor.getEmail() + "Email nou:");
                        input = sc.nextLine();
                        if (input.isEmpty()) input = furnizor.getEmail();
                        furnizor.setEmail(input);
                        try {
                            furnizorRepository.update(furnizor);
                        } catch (RuntimeException e) {
                            System.out.println("Actualizare furnizor eșuată: " + e.getMessage());
                        }
                        System.out.println("Noile date din furnizor: " + furnizor);
                    } catch (NoSuchElementException e) {
                        System.out.println("Nu s-a găsit furnizorul");
                    }
                    asteptareTasta();
                    break;
                }
                case 3: {
                    auditService.logAction("adauga_produs");
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
                    do {
                        System.out.println("CUI furnizor:");
                        cuiFurnizor = sc.nextLine();
                        if (cuiFurnizor.equals("end")) {
                            iesire = true;
                            break;
                        }
                        exista = furnizorService.existaCui(cuiFurnizor);
                        if (!exista) {
                            System.out.println("CUI-ul introdus nu este asociat cu nici-un furnizor (scrie end pentru a iesi din comandă).");
                        }
                    } while (!exista);
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
                    produsService.adaugaProdus(produs);
                    try {
                        produsRepository.save(produs);
                    } catch (RuntimeException e) {
                        System.out.println("Salvare produs eșuată: " + e.getMessage());
                    }
                    System.out.println("S-a introdus produsul: ");
                    System.out.println(produs);
                    asteptareTasta();
                    break;
                }
                case 4: {
                    auditService.logAction("modifica_produs");
                    System.out.println("Modificare produs\n");
                    System.out.println("Introdu codul produsului: ");
                    int codInventar = sc.nextInt();
                    sc.nextLine();
                    try {
                        produsService.cautaDupaId(codInventar);
                        System.out.println("Produs găsit");
                        System.out.println("În câmpurile următoare lasă gol pentru a nu schimba:");
                        System.out.println("Nume vechi: " + produsService.cautaDupaId(codInventar).getNume() + " Nume nou:");
                        String input = sc.nextLine();
                        if (input.isEmpty()) input = produsService.cautaDupaId(codInventar).getNume();
                        produsService.cautaDupaId(codInventar).setNume(input);
                        System.out.println("Preț cumpărare vechi: " + produsService.cautaDupaId(codInventar).getPretCumparare() + " Preț cumpărare nou:");
                        input = sc.nextLine();
                        if (input.isEmpty())
                            input = String.format("%s", produsService.cautaDupaId(codInventar).getPretCumparare());
                        produsService.cautaDupaId(codInventar).setPretCumparare(Integer.parseInt(input));
                        System.out.println("Preț vânzare vechi: " + produsService.cautaDupaId(codInventar).getPretVanzare() + " Preț vânzare nou:");
                        input = sc.nextLine();
                        if (input.isEmpty())
                            input = String.format("%s", produsService.cautaDupaId(codInventar).getPretVanzare());
                        if (Integer.parseInt(input) < produsService.cautaDupaId(codInventar).getPretCumparare())
                            System.out.println("ATENȚIE: Prețul de vânzare este mai mic decât cel de cumpărare!");
                        produsService.cautaDupaId(codInventar).setPretVanzare(Integer.parseInt(input));
                        System.out.println("Categorie veche: " + produsService.cautaDupaId(codInventar).getCategorie() + " Categorie nouă:");
                        input = sc.nextLine();
                        if (input.isEmpty()) input = produsService.cautaDupaId(codInventar).getCategorie();
                        produsService.cautaDupaId(codInventar).setCategorie(input);
                        System.out.println("CUI furnizor vechi: " + produsService.cautaDupaId(codInventar).getCuiFurnizor() + " CUI furnizor nou:");
                        input = sc.nextLine();
                        if (input.isEmpty()) input = produsService.cautaDupaId(codInventar).getCuiFurnizor();
                        produsService.cautaDupaId(codInventar).setCuiFurnizor(input);
                        System.out.println("Stoc minim vechi: " + produsService.cautaDupaId(codInventar).getStocMinim() + " Stoc minim nou:");
                        input = sc.nextLine();
                        if (input.isEmpty())
                            input = String.format("%s", produsService.cautaDupaId(codInventar).getStocMinim());
                        produsService.cautaDupaId(codInventar).setStocMinim(Integer.parseInt(input));
                        System.out.println("Specificații vechi:");
                        for (String spec : produsService.cautaDupaId(codInventar).getSpecificatii()) {
                            System.out.println(spec);
                        }
                        System.out.println("Doriți să schimbați specificațiile? (y/n)");
                        String opt = sc.nextLine();
                        List<String> specificatii = new ArrayList<>();
                        if (opt.equals("y")) {
                            System.out.println("Număr specificații: ");
                            int numSpecificatii = sc.nextInt();
                            sc.nextLine();
                            for (int i = 0; i < numSpecificatii; i++) {
                                specificatii.add(sc.nextLine());
                            }
                            produsService.cautaDupaId(codInventar).setSpecificatii(specificatii);
                        } else specificatii = produsService.cautaDupaId(codInventar).getSpecificatii();
                        produsService.cautaDupaId(codInventar).setSpecificatii(specificatii);
                        try {
                            produsRepository.update(produsService.cautaDupaId(codInventar));
                        } catch (RuntimeException e) {
                            System.out.println("Actualizare produs eșuată: " + e.getMessage());
                        }
                        System.out.println("Noile date din produs: " + produsService.cautaDupaId(codInventar));
                        asteptareTasta();
                        break;
                    } catch (ProdusNegasitException e) {
                        System.out.println(e);
                        asteptareTasta();
                    }

                }
                case 5: {
                    auditService.logAction("receptie_marfa");
                    System.out.println("Recepție marfă\n");
                    System.out.println("Introdu codul produsului: ");
                    int codInventar = sc.nextInt();
                    sc.nextLine();
                    try {
                        produsService.cautaDupaId(codInventar);
                        System.out.println("Număr produse noi:");
                        int numProduse = sc.nextInt();
                        sc.nextLine();
                        if (numProduse <= 0) {
                            System.out.println("Stocul primit nu poate fi negativ. Apasă orice tastă pentru a reveni la meniul principal");
                            sc.nextLine();
                            break;
                        }
                        produsService.cautaDupaId(codInventar).addStoc(numProduse);
                        try {
                            produsRepository.update(produsService.cautaDupaId(codInventar));
                        } catch (RuntimeException e) {
                            System.out.println("Actualizare stoc eșuată: " + e.getMessage());
                        }
                        System.out.println("Stoc adăugat. Stoc nou: " + produsService.cautaDupaId(codInventar).getStoc());
                        asteptareTasta();
                        break;
                    } catch (ProdusNegasitException e) {
                        System.out.println(e);
                        asteptareTasta();
                        break;
                    }
                }
                case 6: {
                    auditService.logAction("modifica_stoc");
                    System.out.println("Modificare stoc\n");
                    System.out.print("Introdu codul produsului: ");
                    int codInventar = sc.nextInt();
                    sc.nextLine();
                    try{
                        produsService.cautaDupaId(codInventar);
                        System.out.println("Stocul actual al produsului " + produsService.cautaDupaId(codInventar).getNume() + " este: " + produsService.cautaDupaId(codInventar).getStoc());
                        System.out.print("Diferență (scrie număr negativ dacă trebuie scăzut inventarul, și pozitiv dacă trebuie să crească: ");
                        int numProduse = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Introdu motivul pentru modificarea stocului: ");
                        String motiv = sc.nextLine();
                        produsService.cautaDupaId(codInventar).addStoc(numProduse);
                        ModificareStocRecord modificareStoc = produsService.addRecordModificareStoc(codInventar, numProduse, motiv, accountService.getLoggedInUser().getUid());
                        try {
                            produsRepository.update(produsService.cautaDupaId(codInventar));
                            modificareStocRepository.save(modificareStoc);
                        } catch (RuntimeException e) {
                            System.out.println("Actualizare stoc eșuată: " + e.getMessage());
                        }
                        System.out.println("Stocul după modificare al produsului " + produsService.cautaDupaId(codInventar).getNume() + " este: " + produsService.cautaDupaId(codInventar).getStoc());
                        if (produsService.cautaDupaId(codInventar).getStoc() <= produsService.cautaDupaId(codInventar).getStocMinim())
                            System.out.println("ALERTĂ! Stocul este sub stocul minim de " + produsService.cautaDupaId(codInventar).getStocMinim());
                        asteptareTasta();
                        break;
                    }catch (ProdusNegasitException e)
                    {
                        System.out.println(e);
                        asteptareTasta();
                        break;
                    }
                }
                case 7: {
                    auditService.logAction("vanzare_produs");
                    System.out.println("Vânzare produse\n");
                    System.out.print("Introdu codul produsului: ");
                    int codInventar = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Câte produse s-au vândut?");
                    int nrProd = sc.nextInt();
                    sc.nextLine();
                    VanzareRecord vanzare;
                    try
                    {
                        vanzare = produsService.vindeProdus(codInventar, nrProd);
                    }catch (ProdusNegasitException | StocInsuficientException | IllegalArgumentException e)
                    {
                        System.out.println(e);
                        asteptareTasta();
                        break;
                    }
                    try {
                        vanzareRepository.recordSaleTransactional(produsService.cautaDupaId(codInventar), vanzare);
                    } catch (SQLException e) {
                        System.out.println("Salvare vânzare eșuată: " + e.getMessage());
                    }
                    System.out.println("Stocul după vânzarea produsului " + produsService.cautaDupaId(codInventar).getNume() + " este: " + produsService.cautaDupaId(codInventar).getStoc());
                    if (produsService.cautaDupaId(codInventar).getStoc() <= produsService.cautaDupaId(codInventar).getStocMinim())
                        System.out.println("ALERTĂ! Stocul este sub stocul minim de " + produsService.cautaDupaId(codInventar).getStocMinim());
                    asteptareTasta();
                    break;
                }
                case 8: {
                    auditService.logAction("afisare_furnizori");
                    System.out.println("Afișare furnizori\n");
                    for (Furnizor furnizor : furnizorService.listeazaToate()) {
                        System.out.println(furnizor.toString());
                    }
                    asteptareTasta();
                    break;
                }
                case 9: {
                    auditService.logAction("afisare_produse");
                    System.out.println("Afișare produse\n");
                    int opt = 0;
                    while (opt != 1 && opt != 2 && opt != 3 && opt != 4) {
                        System.out.println("Alege metoda de sortare: ");
                        System.out.println("1. Criteriu: Nume (A-Z)");
                        System.out.println("2. Criteriu: Nume (Z-A)");
                        System.out.println("3. Criteriu: cod inventar crescător");
                        System.out.println("4. Criteriu: cod inventar descrescător");
                        opt = sc.nextInt();
                        sc.nextLine();
                        if (opt != 1 && opt != 2 && opt != 3 && opt != 4)
                            System.out.println("Introdu un număr între 1 și 4");
                    }
                    System.out.println("Introdu categorie produs (sau keyword-ul 'tot' pentru toate produsele): ");
                    String categorie = sc.nextLine();
                    List<Produs> lista;
                    if (opt == 1)
                    {
                        lista = produsService.listeazaSortatDupaNume();
                    }
                    if (opt == 2)
                    {
                        lista = produsService.listeazaSortatDupaNumeDescrescator();
                    }
                    if (opt == 3)
                    {
                        lista = produsService.listeazaSortatDupaCod();
                    }
                    if (opt == 4)
                    {
                        lista = produsService.listeazaSortatDupaCodDescrescator();
                    }
                    else lista = produsService.listeazaToate();
                    if (categorie.equals("tot")) {
                        for (Produs produs : lista) {
                            System.out.println(produs);
                        }
                        asteptareTasta();
                        break;
                    }
                    boolean gasit = false;
                    for (Produs produs : lista) {
                        if (produs.getCategorie().equals(categorie)) {
                            gasit = true;
                            System.out.println(produs);
                        }
                    }
                    if (!gasit) System.out.println("Nu s-a găsit nici-un produs din categoria " + categorie + ".");
                    asteptareTasta();
                    break;
                }
                case 10: {
                    auditService.logAction("afisare_stoc_critic");
                    System.out.println("Afișare stoc critic\n");
                    for (Produs produs : produsService.listeazaToate()) {
                        if (produs.getStocMinim() > produs.getStoc()) {
                            System.out.println(produs);
                        }
                    }
                    asteptareTasta();
                    break;
                }
                case 11: {
                    auditService.logAction("cauta_produs");
                    System.out.println("Căutare produs\n");
                    System.out.print("Introdu codul produsului: ");
                    int codInventar = sc.nextInt();
                    sc.nextLine();
                    try {
                        System.out.println(produsService.cautaDupaId(codInventar));
                    } catch (ProdusNegasitException e) {
                        System.out.println("Codul introdus nu este asociat cu niciun produs. Apasă orice tastă pentru a reveni la meniul principal");
                        sc.nextLine();
                        break;
                    }
                    asteptareTasta();
                    break;
                }
                case 12: {
                    auditService.logAction("aplica_reducere");
                    System.out.println("Aplicare reducere\n");
                    System.out.print("Introdu codul produsului: ");
                    int codInventar = sc.nextInt();
                    sc.nextLine();
                    try {
                        System.out.println("Reducere actuală: " + produsService.cautaDupaId(codInventar).getProcentReducere() + "%.");
                    } catch (ProdusNegasitException e) {
                        System.out.println("Codul introdus nu este asociat cu niciun produs. Apasă orice tastă pentru a reveni la meniul principal");
                        sc.nextLine();
                        break;
                    }
                    System.out.print("Reducere noua: ");
                    int reducere = sc.nextInt();
                    sc.nextLine();
                    if (reducere > 100) {
                        System.out.println("Reducerea nu poate fi mai mare ca 100! Apasă orice tastă pentru a reveni la meniul principal");
                        sc.nextLine();
                        break;
                    }
                    if (reducere < 0) {
                        System.out.println("Reducerea nu poate fi negativă! Apasă orice tastă pentru a reveni la meniul principal");
                        sc.nextLine();
                        break;
                    }
                    produsService.cautaDupaId(codInventar).setProcentReducere(reducere);
                    try {
                        produsRepository.update(produsService.cautaDupaId(codInventar));
                    } catch (RuntimeException e) {
                        System.out.println("Actualizare reducere eșuată: " + e.getMessage());
                    }
                    System.out.println("S-a aplicat reducerea de " + reducere + "% asupra produsului cu codul " + codInventar + ".");
                    asteptareTasta();
                    break;
                }
                case 13: {
                    auditService.logAction("raport_vanzari");
                    System.out.println("Raport vânzări\n");
                    RaportVanzari raport = produsService.getRaportVanzari();
                    if (raport.getVanzari().isEmpty()) {
                        System.out.println("Nu există vânzări înregistrate.");
                        asteptareTasta();
                        break;
                    }
                    System.out.println("Opțiuni filtrare:");
                    System.out.println("1. Toate vânzările");
                    System.out.println("2. Ultimele N vânzări");
                    System.out.println("3. Vânzări dintr-un interval de timp");
                    System.out.println("4. Vânzările dintr-o anumită categorie");
                    System.out.println("5. Vânzările unui anumit produs");
                    System.out.println("6. Sumar (total vânzări + total unități)");
                    System.out.print("Alegere: ");
                    int filtru = sc.nextInt();
                    sc.nextLine();
                    switch (filtru) {
                        case 1: {
                            for (VanzareRecord vanzare : raport.getVanzari()) {
                                System.out.println(vanzare);
                            }
                            break;
                        }
                        case 2: {
                            System.out.print("N: ");
                            int n = sc.nextInt();
                            sc.nextLine();
                            if (n <= 0) {
                                System.out.println("N trebuie să fie pozitiv.");
                                break;
                            }
                            for (VanzareRecord vanzare : raport.ultimeleVanzari(n)) {
                                System.out.println(vanzare);
                            }
                            break;
                        }
                        case 3: {
                            System.out.print("Start (yyyy-MM-ddTHH:mm:ss): ");
                            String startStr = sc.nextLine();
                            System.out.print("End   (yyyy-MM-ddTHH:mm:ss): ");
                            String endStr = sc.nextLine();
                            try {
                                LocalDateTime start = LocalDateTime.parse(startStr);
                                LocalDateTime end = LocalDateTime.parse(endStr);
                                List<VanzareRecord> vanzariInterval = raport.vanzariDupaInterval(start, end);
                                if (vanzariInterval.isEmpty()) {
                                    System.out.println("Nu există vânzări în intervalul ales.");
                                } else {
                                    for (VanzareRecord vanzare : vanzariInterval) {
                                        System.out.println(vanzare);
                                    }
                                }
                            } catch (DateTimeParseException e) {
                                System.out.println("Format dată invalid. Exemplu valid: 2026-04-25T10:30:00");
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        }
                        case 4: {
                            System.out.println("Introduceți categoria: ");
                            String categorie = sc.nextLine();
                            List<VanzareRecord> vanzariCategorie = raport.vanzariDupaCategorie(categorie);
                            if (vanzariCategorie.isEmpty())
                            {
                                System.out.println("Nu s-au vândut produse din categoria " + categorie + ".");
                            }
                            else {
                                for (VanzareRecord vanzare : vanzariCategorie) {
                                    System.out.println(vanzare);
                                }
                            }
                        }
                        case 5: {
                            System.out.println("Introduceți codul produsului: ");
                            int codInventar = sc.nextInt();
                            sc.nextLine();
                            List<VanzareRecord> vanzariProdus = raport.vanzariDupaProdus(codInventar);
                            if (vanzariProdus.isEmpty())
                            {
                                System.out.println("Nu s-au vândut produse cu codul " + codInventar + ".");
                            }
                            else {
                                for (VanzareRecord vanzare : vanzariProdus) {
                                    System.out.println(vanzare);
                                }
                            }
                        }
                        case 6: {
                            System.out.println(raport);
                            break;
                        }
                        default: {
                            System.out.println("Opțiune invalidă.");
                            break;
                        }
                    }
                    asteptareTasta();
                    break;
                }
                case 14: {
                    auditService.logAction("rapoarte_diverse");
                    System.out.println("Rapoarte diverse\n");
                    System.out.println("1. Produse cu furnizor");
                    System.out.println("2. Modificări stoc cu utilizator");
                    System.out.println("3. Top produse vândute");
                    System.out.print("Alegere: ");
                    int optJoin = sc.nextInt();
                    sc.nextLine();
                    if (optJoin == 1) {
                        for (Map<String, Object> row : produsRepository.findProductsWithSupplier()) {
                            System.out.println(row);
                        }
                    } else if (optJoin == 2) {
                        for (Map<String, Object> row : modificareStocRepository.findStockChangesWithUser()) {
                            System.out.println(row);
                        }
                    } else if (optJoin == 3) {
                        System.out.print("Limită rezultate: ");
                        int limit = sc.nextInt();
                        sc.nextLine();
                        for (Map<String, Object> row : vanzareRepository.findTopSellingProducts(limit)) {
                            System.out.println(row);
                        }
                    } else {
                        System.out.println("Opțiune invalidă.");
                    }
                    asteptareTasta();
                    break;
                }
                case 15: {
                    auditService.logAction("sterge_produs");
                    System.out.println("Ștergere produs\n");
                    System.out.println("Introdu codul produsului: ");
                    int codInventar = sc.nextInt();
                    sc.nextLine();
                    Produs produs;
                    try {
                        produs = produsService.cautaDupaId(codInventar);
                    } catch (ProdusNegasitException e) {
                        System.out.println("Codul introdus nu este asociat cu niciun produs");
                        asteptareTasta();
                        break;
                    }
                    if (produs.getStoc() > 0) {
                        System.out.println("Pentru a elimina un produs stocul acestuia trebuie să fie 0!");
                        asteptareTasta();
                        break;
                    }
                    System.out.println("Confirmă ștergerea produsului (tastează 'yes') " + produs);
                    String confirmare = sc.nextLine();
                    if (confirmare.equals("yes")) {
                        produsService.stergeProdus(codInventar);
                        try {
                            produsRepository.delete(codInventar);
                        } catch (RuntimeException e) {
                            System.out.println("Ștergere produs eșuată: " + e.getMessage());
                        }
                        System.out.println("Produs șters.");
                    }
                    asteptareTasta();
                    break;
                }
                case 16: {
                    auditService.logAction("sterge_furnizor");
                    System.out.println("Ștergere furnizor\n");
                    System.out.println("Introdu CUI-ul furnizorului: ");
                    String cui = sc.nextLine();
                    if (!furnizorService.existaCui(cui)) {
                        System.out.println("CUI-ul introdus nu este asociat cu nici-un furnizor");
                        asteptareTasta();
                        break;
                    }
                    Furnizor furnizor = furnizorService.cautaDupaCui(cui);
                    boolean gasit = false;
                    for (Produs produs : produsService.listeazaToate()) {
                        if (produs.getCuiFurnizor().equals(cui)) {
                            gasit = true;
                            break;
                        }
                    }
                    if (gasit) {
                        System.out.println("Există produse ce provin de la acest furnizor. Pentru a-l putea șterge, șterge mai întâi produsele respective.");
                        asteptareTasta();
                        break;
                    }
                    System.out.println("Se va șterge furnizorul " + furnizor);
                    System.out.println("Scrie 'yes' pentru a confirma!");
                    String opt = sc.nextLine();
                    if (opt.equals("yes")) {
                        furnizorService.stergeFurnizor(cui);
                        try {
                            furnizorRepository.delete(cui);
                        } catch (RuntimeException e) {
                            System.out.println("Ștergere furnizor eșuată: " + e.getMessage());
                        }
                        System.out.println("Ștergere reușită!");
                        asteptareTasta();
                        break;
                    }
                    System.out.println("Nu s-a efectuat ștergerea. Apăsați orice tastă pentru a reveni la meniul principal.");
                    sc.nextLine();
                    break;
                }
                case 17:
                {
                    auditService.logAction("creare_cont_angajat");
                    System.out.println("Creare cont angajat\n");
                    if (accountService.getLoggedInUser().getRol().equals("Angajat") && accountService.getLoggedInUser().getUid()!=0) {
                        System.out.println("Nu ai acces la această opțiune!");
                        asteptareTasta();
                        break;
                    }
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
                                if (utilizatori.get(i).getDataConcediere() != null)
                                {
                                    System.out.println("Nu se poate reangaja un angajat care nu a demisionat!");
                                    asteptareTasta();
                                    break;

                                }
                                System.out.println("S-a găsit angajatul " + utilizatori.get(i).toStringDetaliat());
                                boolean shouldUpdate = false;
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
                                        shouldUpdate = true;
                                    }
                                    else if (rol.equals("manager"))
                                    {
                                        utilizatori.set(i, new Manager(utilizatori.get(i).getUsername(), nume, prenume, salariu, utilizatori.get(i).getCnp(), adresa, telefon, email, parola, utilizatori.get(i).getDataNasterii(), dataAngajare, utilizatori.get(i).getUid()));
                                        shouldUpdate = true;
                                    }
                                    else if (rol.equals("angajat"))
                                    {
                                        utilizatori.set(i, new Angajat(utilizatori.get(i).getUsername(), nume, prenume, salariu, utilizatori.get(i).getCnp(), adresa, telefon, email, parola, utilizatori.get(i).getDataNasterii(), dataAngajare, utilizatori.get(i).getUid()));
                                        shouldUpdate = true;
                                    }
                                    else System.out.println("Rol invalid");
                                }
                                else
                                {
                                    System.out.print("Data angajare:");
                                    String dataAngajare = sc.nextLine();
                                    utilizatori.get(i).reangajare(dataAngajare);
                                    shouldUpdate = true;
                                }
                                if (shouldUpdate) {
                                    try {
                                        utilizatorRepository.update(utilizatori.get(i));
                                    } catch (RuntimeException e) {
                                        System.out.println("Actualizare angajat eșuată: " + e.getMessage());
                                    }
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
                        while (username.equals("admin") || username.isEmpty())
                        {
                            System.out.print("Username-ul nu poate fi 'admin' sau gol!, username:");
                            username = sc.nextLine();
                        }
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
                            try {
                                utilizatorRepository.save(utilizator);
                            } catch (RuntimeException e) {
                                System.out.println("Creare cont eșuată: " + e.getMessage());
                            }
                        }
                        else if (rol.equals("manager")) {
                            Utilizator utilizator = new Manager(username, nume, prenume, salariu, cnp, adresa, telefon, email, parola, dataNasterii, dataAngajare, utilizatori.size());
                            utilizatori.add(utilizator);
                            try {
                                utilizatorRepository.save(utilizator);
                            } catch (RuntimeException e) {
                                System.out.println("Creare cont eșuată: " + e.getMessage());
                            }
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
                case 18:
                {
                    auditService.logAction("detalii_angajati");
                    System.out.println("Detalii angajati\n");
                    if (accountService.getLoggedInUser().getRol().equals("Angajat") && accountService.getLoggedInUser().getUid()!=0) {
                        System.out.println("Nu ai acces la această opțiune!");
                        asteptareTasta();
                        break;
                    }
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
                case 19:
                {
                    auditService.logAction("modifica_angajat");
                    System.out.println("Modificare angajat\n");
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
                            boolean shouldUpdate = false;
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
                            System.out.print("Rol(angajat / manager):");
                            String rol = sc.nextLine();
                            if (rol.equals("manager"))
                            {
                                utilizatori.set(i, new Manager(utilizatori.get(i).getUsername(), nume, prenume, salariu, utilizatori.get(i).getCnp(), adresa, telefon, email, parola, utilizatori.get(i).getDataNasterii(), utilizatori.get(i).getDataAngajare(), utilizatori.get(i).getUid()));
                                shouldUpdate = true;
                            }
                            else if (rol.equals("angajat"))
                            {
                                utilizatori.set(i, new Angajat(utilizatori.get(i).getUsername(), nume, prenume, salariu, utilizatori.get(i).getCnp(), adresa, telefon, email, parola, utilizatori.get(i).getDataNasterii(), utilizatori.get(i).getDataAngajare(), utilizatori.get(i).getUid()));
                                shouldUpdate = true;
                            }
                            else System.out.println("Rol invalid");
                            if (shouldUpdate) {
                                try {
                                    utilizatorRepository.update(utilizatori.get(i));
                                } catch (RuntimeException e) {
                                    System.out.println("Actualizare angajat eșuată: " + e.getMessage());
                                }
                            }
                        }
                    }
                    if (!gasit)
                    {
                        System.out.println("Angajatul nu a fost gasit");
                    }
                    break;
                }
                case 20:
                {
                    auditService.logAction("sterge_angajat");
                    System.out.println("Stergere angajat\n");
                    if (accountService.getLoggedInUser().getRol().equals("Angajat") && accountService.getLoggedInUser().getUid()!=0) {
                        System.out.println("Nu ai acces la această opțiune!");
                        asteptareTasta();
                        break;
                    }
                    System.out.println("Introduceți data concedierii:");
                    String dataConcediere = sc.nextLine();
                    System.out.println("Introduceți uid-ul angajatului:");
                    int input = sc.nextInt();
                    sc.nextLine();
                    if (input == 0)
                    {
                        System.out.println("Admin-ul nu poate fi șters.");
                        asteptareTasta();
                        break;
                    }
                    boolean gasit = false;
                    for (Utilizator utilizator : utilizatori) {

                        if (utilizator.getUid() == input) {
                            utilizator.concediere(dataConcediere);
                            try {
                                utilizatorRepository.update(utilizator);
                            } catch (RuntimeException e) {
                                System.out.println("Actualizare concediere eșuată: " + e.getMessage());
                            }
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