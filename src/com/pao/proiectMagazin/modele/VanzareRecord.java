package com.pao.proiectMagazin.modele;

import java.time.LocalDateTime;
import java.util.Objects;

public final class VanzareRecord {
    private final int codProdus;
    private final String numeProdus;
    private final int cantitate;
    private final int pretUnitar;
    private final int total;
    private final String categorieProdus;
    private final LocalDateTime timestamp;

    public VanzareRecord(int codProdus, String numeProdus, int cantitate, int pretUnitar, String categorieProdus, LocalDateTime timestamp) {
        if (numeProdus == null || numeProdus.isBlank()) {
            throw new IllegalArgumentException("numeProdus nu poate fi null sau gol");
        }
        if (cantitate <= 0) {
            throw new IllegalArgumentException("cantitate trebuie să fie pozitivă");
        }
        if (pretUnitar < 0) {
            throw new IllegalArgumentException("pretUnitar nu poate fi negativ");
        }
        this.codProdus = codProdus;
        this.numeProdus = numeProdus;
        this.cantitate = cantitate;
        this.pretUnitar = pretUnitar;
        this.total = cantitate * pretUnitar;
        this.categorieProdus = categorieProdus;
        this.timestamp = Objects.requireNonNull(timestamp, "timestamp nu poate fi null");
    }

    public int getCodProdus() {
        return codProdus;
    }

    public String getNumeProdus() {
        return numeProdus;
    }

    public int getCantitate() {
        return cantitate;
    }

    public int getPretUnitar() {
        return pretUnitar;
    }

    public int getTotal() {
        return total;
    }

    public String getCategorieProdus() {
        return categorieProdus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format(
                "VanzareRecord{codProdus=%d, numeProdus='%s', cantitate=%d, pretUnitar=%d, total=%d, timestamp=%s}",
                codProdus, numeProdus, cantitate, pretUnitar, total, timestamp
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof VanzareRecord)) return false;
        VanzareRecord that = (VanzareRecord) obj;
        return codProdus == that.codProdus
                && cantitate == that.cantitate
                && pretUnitar == that.pretUnitar
                && total == that.total
                && Objects.equals(numeProdus, that.numeProdus)
                && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codProdus, numeProdus, cantitate, pretUnitar, total, timestamp);
    }
}