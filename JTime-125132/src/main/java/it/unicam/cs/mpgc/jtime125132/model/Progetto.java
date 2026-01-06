package it.unicam.cs.mpgc.jtime125132.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public final class Progetto implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    private final String id;
    private String nome;
    private StatoProgetto stato = StatoProgetto.ATTIVO;
    private final Set<String> attivita = new LinkedHashSet<>();

    public Progetto(String id, String nome) {
        this.id = Objects.requireNonNull(id);
        this.nome = Objects.requireNonNull(nome);
    }

    public void rinomina(String nuovoNome) { this.nome = Objects.requireNonNull(nuovoNome); }

    public void aggiungiAttivita(String idAttivita) { attivita.add(Objects.requireNonNull(idAttivita)); }
    public void rimuoviAttivita(String idAttivita) { attivita.remove(idAttivita); }

    public void chiudi(boolean haAttivitaPendenti) {
        if (haAttivitaPendenti) throw new IllegalStateException("Non puoi chiudere: ci sono attività non completate.");
        this.stato = StatoProgetto.CHIUSO;
    }

    public String id() { return id; }
    public String nome() { return nome; }
    public StatoProgetto stato() { return stato; }
    public Set<String> attivita() { return Set.copyOf(attivita); }

    @Override public String toString() { return nome + " [" + stato + "]"; }
}