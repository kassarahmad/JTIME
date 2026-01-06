package it.unicam.cs.mpgc.jtime125132.service;

import it.unicam.cs.mpgc.jtime125132.model.Pianificazione;
import it.unicam.cs.mpgc.jtime125132.persistence.RepositoryAttivita;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class ServizioPianificazione {

    private final Pianificazione pianificazione;
    private final RepositoryAttivita repoAttivita;

    public ServizioPianificazione(Pianificazione pianificazione, RepositoryAttivita repoAttivita) {
        this.pianificazione = Objects.requireNonNull(pianificazione);
        this.repoAttivita = Objects.requireNonNull(repoAttivita);
    }

    public void pianifica(LocalDate data, String idAttivita) {
        Objects.requireNonNull(data);
        Objects.requireNonNull(idAttivita);
        var a = repoAttivita.get(idAttivita);
        if (a == null) throw new IllegalArgumentException("Attività inesistente.");
        if (a.completata()) throw new IllegalStateException("Non puoi pianificare un'attività completata.");
        pianificazione.pianifica(data, idAttivita);
    }

    public void rimuovi(LocalDate data, String idAttivita) {
        pianificazione.rimuovi(data, idAttivita);
    }

    public List<String> pianificate(LocalDate data) {
        return pianificazione.pianificate(data);
    }

    public void rimuoviAttivitaDaTutteLeDate(Set<String> ids) {
        pianificazione.rimuoviAttivitaDaTutteLeDate(ids);
    }
}