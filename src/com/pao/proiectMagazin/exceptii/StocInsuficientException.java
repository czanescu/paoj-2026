package com.pao.proiectMagazin.exceptii;

public class StocInsuficientException extends RuntimeException {
    public StocInsuficientException(String message) {
        super(message);
    }
}
