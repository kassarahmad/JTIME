package it.unicam.cs.mpgc.jtime125132.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;

public final class StimaTempo implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    private final Duration stimata;
    private Duration effettiva;

    public StimaTempo(Duration stimata) {
        this.stimata = Objects.requireNonNull(stimata);
        if (stimata.isZero() || stimata.isNegative()) throw new IllegalArgumentException("Stima non valida.");
    }

    public void registraEffettiva(Duration d) {
        Objects.requireNonNull(d);
        if (d.isZero() || d.isNegative()) throw new IllegalArgumentException("Durata effettiva non valida.");
        this.effettiva = d;
    }

    public Duration stimata() { return stimata; }
    public Duration effettiva() { return effettiva; }
    public Duration scostamento() { return effettiva == null ? Duration.ZERO : effettiva.minus(stimata); }
}