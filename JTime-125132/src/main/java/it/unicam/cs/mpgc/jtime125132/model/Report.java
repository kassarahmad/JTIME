package it.unicam.cs.mpgc.jtime125132.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public final class Report implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    private final String contenuto;
    private final LocalDateTime creatoIl;

    public Report(String contenuto) {
        this.contenuto = Objects.requireNonNull(contenuto);
        this.creatoIl = LocalDateTime.now();
    }

    public String contenuto() { return contenuto; }
    public LocalDateTime creatoIl() { return creatoIl; }
}