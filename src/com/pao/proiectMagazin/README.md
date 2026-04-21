# Proiect Magazin

## Listă acțiuni/interogări posibile:

### 0. Login Angajat (prerequisite)
Nu e o acțiune în meniu per se, ci doar un login când se pornește programul. În funcție de contul ales, unele opțiuni pot să nu apară.
### 1. Adăugare furnizor
Se introduce numele, cui-ul, telefonul, email-ul și adresa firmei.
### 2. Adăugare produs
Se introduce numele produsului, prețul de cumpărare, prețul de vânzare, categoria, furnizorul, stocul minim acceptat și o listă de specificații.
### 3. Modificare produs
Se introduce uid-ul produsului și apoi se alege ce aspect trebuie modificat.
### 4. Recepție marfă
Se introduce uid-ul produsului și cât stoc a fost recepționat.
### 5. Modificare stoc
Se introduce uid-ul unui produs, se afișează stocul actual, se introduce stocul modificat, și se oferă o motivație pentru schimbarea făcută.
### 6. Vânzare produs
Se introduce uid-ul unui produs și cantitatea vândută.
### 7. Afișare produse
Se introduce categoria (sau „tot” pentru toate produsele), apoi se alege o modalitate de sortare.
### 8. Afișare stoc critic
Se afișează produsele cu stoc mai mic decât stocul minim acceptat.
### 9. Căutare produs
Se introduce uid-ul produsului și se afișează toate datele aferente acestuia.
### 10.Aplică reducere
Se introduce uid-ul produsului și o reducere în procente. Tastați 0 pentru a anula reducerea aplicată deja.
### 11.Raport Vânzări
Se afișează un raport al vânzărilor în funcție de parametrii de filtrare aleși de utilizator (ex ultimele 10 vânzări, o anume categorie, etc.).
### 12.Ștergere produs
Se șterge un produs în funcție de uid (trebuie să nu fie în stoc).
### 13.Creare cont angajat (dacă utilizatorul e manager)
Se introduce numele, prenumele, adresa(poate fi null), email-ul, numărul de telefon, cnp-ul,  rolul, data angajării, data nașterii și parola.
### 14.Detalii Angajati
apare doar pentru manageri, permite fie afisarea tuturor angajatilor, fie afisarea detaliilor unui angajat.
### 15.Ștergere angajat
Se introduce uid-ul angajatului și data concedierii (nu e șters din baza de date, doar nu se mai poate loga)


## Obiecte:

