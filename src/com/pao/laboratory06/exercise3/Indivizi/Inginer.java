package com.pao.laboratory06.exercise3.Indivizi;

import com.pao.laboratory06.exercise3.Interfete.PlataOnline;

public class Inginer extends Angajat implements PlataOnline, Comparable<Inginer> {
    // nu reușesc să înțeleg de ce implementez PlataOnline în Inginer, dar se cere, deci o să o fac
    public Inginer(String nume, String prenume, String telefon, double sold, double salariu) {
        this.nume = nume;
        this.prenume = prenume;
        this.telefon = telefon;
        this.sold = sold;
        this.autentificat = false;
        this.salariu = salariu;
    }

    @Override
    public int compareTo(Inginer o) {
        return String.CASE_INSENSITIVE_ORDER.compare(this.nume, o.nume);
    }

    @Override
    public void autentificare(String user, String parola){
        // nu am vreo metodă de creere a conturilor (aș fi putut totuși să o fac în constructor), deci doar verific dacă sunt null
        if (user == null || user.isBlank() || parola == null || parola.isBlank()) {
            throw new IllegalArgumentException("User-ul și parola nu pot fi nule sau goale.");
        }
        autentificat = true;
    }

    @Override
    public double consultareSold() {
        if (!autentificat) {
            throw new IllegalStateException("Trebuie să te autentifici înainte de a consulta soldul.");
        }
        return sold;
    }

    @Override
    public boolean efectuarePlata(double suma) {
        if (!autentificat) {
            throw new IllegalStateException("Trebuie să te autentifici înainte de a efectua o plată.");
        }
        if (suma <= 0) {
            throw new IllegalArgumentException("Suma trebuie să fie pozitivă.");
        }
        if (sold < suma) {
            return false;
        }
        sold -= suma;
        return true;
    }

}
