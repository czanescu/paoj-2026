package com.pao.proiectMagazin.Servicii;

import com.pao.proiectMagazin.Modele.Utilizator;

public class UserAccountService {
    private static UserAccountService instance;
    private Utilizator loggedInUser;

    private UserAccountService() {
    }

    public static synchronized UserAccountService getInstance() {
        if (instance == null) {
            instance = new UserAccountService();
        }
        return instance;
    }

    public void login(Utilizator user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.loggedInUser = user;
    }

    public Utilizator getLoggedInUser() {
        return loggedInUser;
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }
}
