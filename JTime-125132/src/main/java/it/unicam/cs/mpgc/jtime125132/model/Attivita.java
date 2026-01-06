package it.unicam.cs.mpgc.jtime125132.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;

public final class Attivita implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    private final String id;
    private final String idProgetto;
    private final String titolo;
    private final String descrizione;
    private StatoAttivita stato = StatoAttivita.DA_FARE;
    private final StimaTempo stima;

    public Attivita(String id, String idProgetto, String titolo, String descrizione, Duration stimata) {
        this.id = Objects.requireNonNull(id);
        this.idProgetto = Objects.requireNonNull(idProgetto);
        this.titolo = Objects.requireNonNull(titolo);
        this.descrizione = descrizione == null ? "" : descrizione.trim();
        this.stima = new StimaTempo(Objects.requireNonNull(stimata));
    }

    public void completa(Duration effettiva) {
        stima.registraEffettiva(effettiva);
        stato = StatoAttivita.COMPLETATA;
    }

    public boolean completata() { return stato == StatoAttivita.COMPLETATA; }

    public String id() { return id; }
    public String idProgetto() { return idProgetto; }
    public String titolo() { return titolo; }
    public String descrizione() { return descrizione; }
    public StatoAttivita stato() { return stato; }
    public StimaTempo stima() { return stima; }

    @Override public String toString() { return titolo + " (" + stato + ")"; }
}