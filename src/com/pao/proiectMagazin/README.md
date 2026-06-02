# Proiect Magazin

## Listă acțiuni/interogări posibile:

### 0. Login Angajat (prerequisite)
Nu e o acțiune în meniu per se, ci doar un login când se pornește programul. În funcție de contul ales, unele opțiuni pot să nu apară.
### 1. Adăugare furnizor
Se introduce numele, cui-ul, telefonul, email-ul și adresa firmei.
### 2. Modificare furnizor
Se găsește furnizorul după CUI, și apoi utilizatorul are opțiunea de a schimba numele, adresa, telefonul și email-ul.
### 3. Adăugare produs
Se introduce numele, prețul de cumpărare, prețul de vânzare, categoria, CUI-ul furnizorului, stocul minim, și specificațiile aferente, iar aplicația îi asignează un cod de inventar.
### 4. Modificare produs
Se introduce uid-ul produsului și apoi utilizatorul poate schimba majoritatea datelor aferente
### 5. Recepție marfă
Se introduce uid-ul produsului și cât stoc a fost recepționat (poate fi recepționat doar stoc pozitiv).
### 6. Modificare stoc
Se introduce uid-ul unui produs, se afișează stocul actual, se introduce diferența cu care se dorește modificarea, și se oferă o motivație pentru schimbarea făcută.
### 7. Vânzare produs
Se introduce uid-ul unui produs și cantitatea vândută.
### 8. Afișare furnizori
Se afișează toți furnizorii (toate datele lor)
### 9. Afișare produse
Se alege o modalitate de sortare și apoi fie categoria, fie 'tot' pentru a afișa toate produsele
### 10. Afișare stoc critic
Se afișează produsele cu stoc mai mic decât stocul minim acceptat.
### 11. Căutare produs
Se introduce uid-ul produsului și se afișează toate datele aferente acestuia.
### 12. Aplică reducere
Se introduce uid-ul produsului și o reducere în procente. Tastați 0 pentru a anula reducerea aplicată deja.
### 13. Raport Vânzări
Se afișează un raport al vânzărilor în funcție de parametrii de filtrare aleși de utilizator (ex ultimele 10 vânzări, o anume categorie, etc.).
### 14. Rapoarte diverse
Se afișează rapoarte bazate pe interogări SQL cu JOIN:
- produse cu furnizor
- modificări stoc cu utilizator
- top produse vândute
### 15. Ștergere produs
Se șterge un produs în funcție de uid (trebuie să nu fie în stoc).
### 16. Ștergere furnizor
Se șterge un furnizor din baza de date (trebuie să nu mai existe nici-un produs asociat cu acesta).
### 17. Creare cont angajat (dacă utilizatorul e manager sau admin)
Se introduce numele, prenumele, adresa(poate fi null), email-ul, numărul de telefon, cnp-ul, rolul, data angajării, data nașterii și parola.
### 18. Detalii Angajati (dacă utilizatorul e manager sau admin)
apare doar pentru manageri, permite fie afisarea tuturor angajatilor, fie afisarea detaliilor unui angajat.
### 19. Modificare angajat (dacă utilizatorul e manager sau admin)
Se introduce uid-ul angajatului și pot fi modificate datele acestuia (inclusiv rolul).
### 20. Ștergere angajat (dacă utilizatorul e manager sau admin)
Se introduce uid-ul angajatului și data concedierii (nu e șters din baza de date, doar nu se mai poate loga)


## Obiecte:
- `Produs`
- `Furnizor`
- `Angajat`
- `Manager`
- `Persoana`
- `ModificareStocRecord`
- `RaportVanzari`
- `VanzareRecord`
