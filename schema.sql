DROP TABLE IF EXISTS produs_specificatie;
DROP TABLE IF EXISTS vanzare;
DROP TABLE IF EXISTS modificare_stoc;
DROP TABLE IF EXISTS produs;
DROP TABLE IF EXISTS furnizor;
DROP TABLE IF EXISTS utilizator;

CREATE TABLE furnizor (
    cui VARCHAR(32) PRIMARY KEY,
    nume VARCHAR(255) NOT NULL,
    adresa VARCHAR(255),
    telefon VARCHAR(32),
    email VARCHAR(255)
);

CREATE TABLE utilizator (
    uid INT PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    nume VARCHAR(100) NOT NULL,
    prenume VARCHAR(100) NOT NULL,
    salariu INT NOT NULL,
    cnp VARCHAR(32) NOT NULL,
    adresa VARCHAR(255),
    telefon VARCHAR(32),
    email VARCHAR(255),
    parola VARCHAR(255) NOT NULL,
    data_nasterii VARCHAR(32),
    data_angajare VARCHAR(32),
    data_concediere VARCHAR(32),
    rol VARCHAR(32) NOT NULL
);

CREATE TABLE produs (
    cod_inventar INT PRIMARY KEY,
    nume VARCHAR(255) NOT NULL,
    pret_cumparare INT NOT NULL,
    pret_vanzare INT NOT NULL,
    categorie VARCHAR(100),
    cui_furnizor VARCHAR(32) NOT NULL,
    stoc_minim INT NOT NULL,
    stoc INT NOT NULL,
    procent_reducere INT NOT NULL,
    CONSTRAINT fk_produs_furnizor FOREIGN KEY (cui_furnizor) REFERENCES furnizor(cui)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE produs_specificatie (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cod_inventar INT NOT NULL,
    specificatie VARCHAR(255) NOT NULL,
    CONSTRAINT fk_spec_produs FOREIGN KEY (cod_inventar) REFERENCES produs(cod_inventar)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE vanzare (
    cod_produs INT NOT NULL,
    nume_produs VARCHAR(255) NOT NULL,
    cantitate INT NOT NULL,
    pret_unitar INT NOT NULL,
    total INT NOT NULL,
    categorie VARCHAR(100),
    timestamp DATETIME NOT NULL,
    CONSTRAINT fk_vanzare_produs FOREIGN KEY (cod_produs) REFERENCES produs(cod_inventar)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    PRIMARY KEY (cod_produs, timestamp)
);

CREATE TABLE modificare_stoc (
    cod_produs INT NOT NULL,
    nume_produs VARCHAR(255) NOT NULL,
    stoc_anterior INT NOT NULL,
    stoc_nou INT NOT NULL,
    diferenta INT NOT NULL,
    motiv VARCHAR(255) NOT NULL,
    uid_utilizator INT NOT NULL,
    timestamp DATETIME NOT NULL,
    CONSTRAINT fk_mod_stoc_produs FOREIGN KEY (cod_produs) REFERENCES produs(cod_inventar)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_mod_stoc_user FOREIGN KEY (uid_utilizator) REFERENCES utilizator(uid)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    PRIMARY KEY (cod_produs, timestamp)
);
