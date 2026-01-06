package it.unicam.cs.mpgc.jtime125132.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public final class IntervalloTemporale implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    private final LocalDate da;
    private final LocalDate a;

    public IntervalloTemporale(LocalDate da, LocalDate a) {
        this.da = Objects.requireNonNull(da);
        this.a = Objects.requireNonNull(a);
        if (a.isBefore(da)) throw new IllegalArgumentException("Intervallo non valido: 'a' prima di 'da'.");
    }

    public LocalDate da() { return da; }
    public LocalDate a() { return a; }
}