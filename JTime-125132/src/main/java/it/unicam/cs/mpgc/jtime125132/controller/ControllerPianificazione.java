package it.unicam.cs.mpgc.jtime125132.controller;

import it.unicam.cs.mpgc.jtime125132.model.Attivita;
import it.unicam.cs.mpgc.jtime125132.persistence.RepositoryAttivita;
import it.unicam.cs.mpgc.jtime125132.service.ServizioPianificazione;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public final class ControllerPianificazione {

    private final ServizioPianificazione servizio;
    private final RepositoryAttivita repoAttivita;

    public ControllerPianificazione(ServizioPianificazione servizio, RepositoryAttivita repoAttivita) {
        this.servizio = Objects.requireNonNull(servizio);
        this.repoAttivita = Objects.requireNonNull(repoAttivita);
    }

    public void pianifica(LocalDate data, String idAttivita) { servizio.pianifica(data, idAttivita); }
    public void rimuovi(LocalDate data, String idAttivita) { servizio.rimuovi(data, idAttivita); }

    public List<Attivita> pianificate(LocalDate data) {
        return servizio.pianificate(data).stream()
                .map(repoAttivita::get)
                .filter(Objects::nonNull)
                .toList();
    }
}