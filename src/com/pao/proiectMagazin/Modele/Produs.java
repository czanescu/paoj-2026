package com.pao.proiectMagazin.Modele;

import java.util.List;

public class Produs {
    String nume;
    int pretCumparare;
    int pretVanzare;
    String categorie;
    String cuiFurnizor;
    int stocMinim;
    int codInventar;
    int stoc;
    int procentReducere;
    List<String> specificatii;

    public Produs(int codInventar, String nume, int pretCumparare, int pretVanzare, String categorie, String cuiFurnizor, int stocMinim,int stoc, int procentReducere, List<String> specificatii){
        this.codInventar = codInventar;
        this.nume = nume;
        this.pretCumparare = pretCumparare;
        this.pretVanzare = pretVanzare;
        this.categorie = categorie;
        this.cuiFurnizor = cuiFurnizor;
        this.stocMinim = stocMinim;
        this.specificatii = specificatii;
        this.procentReducere = procentReducere;
        this.stoc = stoc;
    }

    public Produs(int codInventar, String nume, int pretCumparare, int pretVanzare, String categorie, String cuiFurnizor, int stocMinim, int procentReducere, List<String> specificatii){
        this.codInventar = codInventar;
        this.nume = nume;
        this.pretCumparare = pretCumparare;
        this.pretVanzare = pretVanzare;
        this.categorie = categorie;
        this.cuiFurnizor = cuiFurnizor;
        this.stocMinim = stocMinim;
        this.specificatii = specificatii;
        this.procentReducere = procentReducere;
        this.stoc = 0;
    }
    public int getCodInventar(){return codInventar;}
    public String getNume(){return nume;}
    public int getPretCumparare(){return pretCumparare;}
    public int getPretVanzare(){return pretVanzare;}
    public String getCategorie(){return categorie;}
    public String getCuiFurnizor(){return cuiFurnizor;}
    public int getStocMinim(){return stocMinim;}
    public List<String> getSpecificatii(){return specificatii;}
    public int getStoc(){return stoc;}
    public int getProcentReducere(){return procentReducere;}

    public void setNume(String nume){this.nume = nume;}
    public void setPretCumparare(int pretCumparare){this.pretCumparare = pretCumparare;}
    public void setPretVanzare(int pretVanzare){this.pretVanzare = pretVanzare;}
    public void setCategorie(String categorie){this.categorie = categorie;}
    public void setCuiFurnizor(String cuiFurnizor){this.cuiFurnizor = cuiFurnizor;}
    public void setStocMinim(int stocMinim){this.stocMinim = stocMinim;}
    public void setSpecificatii(List<String> specificatii){this.specificatii = specificatii;}
    public void setProcentReducere(int procentReducere){this.procentReducere = procentReducere;}

    public void addStoc(int nr){stoc += nr;}
    public void vanzare(int nr){stoc -=nr;}

    public String toString()
    {
        return String.format("Cod inventar: %d, Nume: %s, Stoc: %s, Pret cumparare: %d, Pret vanzare: %d, reducere: %d%%, Categorie: %s, CUI furnizor: %s, Stoc minim: %d, Specificatii: %s", codInventar, nume, stoc, pretCumparare, pretVanzare, procentReducere, categorie, cuiFurnizor, stocMinim, specificatii);
    }
}
