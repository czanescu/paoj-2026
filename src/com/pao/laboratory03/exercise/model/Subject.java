package com.pao.laboratory03.exercise.model;

public enum Subject {
    PAOJ(6, "Programare Avansata pe Obiecte") {
        @Override
        public String toString() {
            return "PAOJ: " + getFullName() + " (" + getCredits() + " credite)";
        }
    },
    BD(3, "Baze de Date") {
        @Override
        public String toString() {
            return "BD: " + getFullName() + " (" + getCredits() + " credite)";
        }
    },
    SO(3, "Sisteme de Operare") {
        @Override
        public String toString() {
            return "SO: " + getFullName() + " (" + getCredits() + " credite)";
        }
    },
    RC(4, "Rețele de Calculatoare") {
        @Override
        public String toString() {
            return "RC: " + getFullName() + " (" + getCredits() + " credite)";
        }
    };
    private String fullName;
    private int credits;

    Subject(int credits, String fullName){
        this.credits = credits;
        this.fullName = fullName;
    }

    public String getFullName() {return fullName;}
    public int getCredits() {return credits;}
    public abstract String toString();

}