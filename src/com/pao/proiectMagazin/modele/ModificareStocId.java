package com.pao.proiectMagazin.modele;

import java.time.LocalDateTime;
import java.util.Objects;

public class ModificareStocId {
    private final int codProdus;
    private final LocalDateTime timestamp;

    public ModificareStocId(int codProdus, LocalDateTime timestamp) {
        this.codProdus = codProdus;
        this.timestamp = Objects.requireNonNull(timestamp, "timestamp-ul nu poate fi null");
    }

    public int getCodProdus() {
        return codProdus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ModificareStocId)) return false;
        ModificareStocId other = (ModificareStocId) obj;
        return codProdus == other.codProdus && Objects.equals(timestamp, other.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codProdus, timestamp);
    }
}
