package com.pao.proiectMagazin.modele;

import java.time.LocalDateTime;
import java.util.Objects;

public final class ModificareStocRecord {
    private final int codProdus;
    private final String numeProdus;
    private final int stocAnterior;
    private final int stocNou;
    private final int diferenta;
    private final String motiv;
    private final int uidUtilizator;
    private final LocalDateTime timestamp;

    public ModificareStocRecord(
        int codProdus,
        String numeProdus,
        int stocAnterior,
        int stocNou,
        String motiv,
        int uidUtilizator
    )
    {
        if (numeProdus == null || numeProdus.isBlank()) {
            throw new IllegalArgumentException("numeProdus nu poate fi null sau gol");
        }
        if (motiv == null || motiv.isBlank()) {
            throw new IllegalArgumentException("motiv nu poate fi null sau gol");
        }
        this.codProdus = codProdus;
        this.numeProdus = numeProdus;
        this.stocAnterior = stocAnterior;
        this.stocNou = stocNou;
        this.diferenta = stocNou - stocAnterior;
        this.motiv = motiv;
        this.uidUtilizator = uidUtilizator;
        this.timestamp = LocalDateTime.now();
    }
    public ModificareStocRecord(
            int codProdus,
            String numeProdus,
            int stocAnterior,
            int stocNou,
            String motiv,
            int uidUtilizator,
            LocalDateTime timestamp
    )
    {
        if (numeProdus == null || numeProdus.isBlank()) {
            throw new IllegalArgumentException("numeProdus nu poate fi null sau gol");
        }
        if (motiv == null || motiv.isBlank()) {
            throw new IllegalArgumentException("motiv nu poate fi null sau gol");
        }
        this.codProdus = codProdus;
        this.numeProdus = numeProdus;
        this.stocAnterior = stocAnterior;
        this.stocNou = stocNou;
        this.diferenta = stocNou - stocAnterior;
        this.motiv = motiv;
        this.uidUtilizator = uidUtilizator;
        this.timestamp = timestamp;
    }

    public int getCodProdus() {
        return codProdus;
    }

    public String getNumeProdus() {
        return numeProdus;
    }

    public int getStocAnterior() {
        return stocAnterior;
    }

    public int getStocNou() {
        return stocNou;
    }

    public int getDiferenta() {
        return diferenta;
    }

    public String getMotiv() {
        return motiv;
    }

    public int getUidUtilizator() {
        return uidUtilizator;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format(
                "ModificareStocRecord{cod=%d, nume='%s', stoc: %d -> %d (%+d), motiv='%s', user=%s (uid=%d), timestamp=%s}",
                codProdus, numeProdus, stocAnterior, stocNou, diferenta, motiv, uidUtilizator, timestamp
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ModificareStocRecord)) return false;
        ModificareStocRecord that = (ModificareStocRecord) obj;
        return codProdus == that.codProdus
                && stocAnterior == that.stocAnterior
                && stocNou == that.stocNou
                && diferenta == that.diferenta
                && uidUtilizator == that.uidUtilizator
                && Objects.equals(numeProdus, that.numeProdus)
                && Objects.equals(motiv, that.motiv)
                && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codProdus, numeProdus, stocAnterior, stocNou, diferenta, motiv, uidUtilizator, timestamp);
    }
}