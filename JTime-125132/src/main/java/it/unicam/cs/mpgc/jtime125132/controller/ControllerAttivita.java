package it.unicam.cs.mpgc.jtime125132.controller;

import it.unicam.cs.mpgc.jtime125132.model.Attivita;
import it.unicam.cs.mpgc.jtime125132.persistence.RepositoryAttivita;
import it.unicam.cs.mpgc.jtime125132.persistence.RepositoryProgetti;
import it.unicam.cs.mpgc.jtime125132.service.ServizioTracciamentoTempo;
import it.unicam.cs.mpgc.jtime125132.util.GeneratoreIdentificativi;
import it.unicam.cs.mpgc.jtime125132.util.ValidatoreInput;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public final class ControllerAttivita {

    private final RepositoryProgetti repoProgetti;
    private final RepositoryAttivita repoAttivita;
    private final ServizioTracciamentoTempo tempo;

    public ControllerAttivita(RepositoryProgetti repoProgetti, RepositoryAttivita repoAttivita, ServizioTracciamentoTempo tempo) {
        this.repoProgetti = Objects.requireNonNull(repoProgetti);
        this.repoAttivita = Objects.requireNonNull(repoAttivita);
        this.tempo = Objects.requireNonNull(tempo);
    }

    public Attivita creaAttivita(String idProgetto, String titolo, String descrizione, int minutiStimati) {
        Objects.requireNonNull(repoProgetti.get(idProgetto), "Progetto inesistente.");
        String t = ValidatoreInput.nonVuota(titolo, "Titolo attività");
        int m = ValidatoreInput.minutiPositivi(minutiStimati, "Minuti stimati");
        Duration stimata = tempo.minuti(m);

        Attivita a = new Attivita(GeneratoreIdentificativi.nuovoId(), idProgetto, t, descrizione, stimata);
        repoAttivita.salva(a);

        var p = repoProgetti.get(idProgetto);
        p.aggiungiAttivita(a.id());
        repoProgetti.salva(p);

        return a;
    }

    public void completa(String idAttivita, int minutiEffettivi) {
        int m = ValidatoreInput.minutiPositivi(minutiEffettivi, "Minuti effettivi");
        var a = Objects.requireNonNull(repoAttivita.get(idAttivita), "Attività inesistente.");
        a.completa(tempo.minuti(m));
        repoAttivita.salva(a);
    }

    public void elimina(String idAttivita) {
        var a = Objects.requireNonNull(repoAttivita.get(idAttivita), "Attività inesistente.");
        var p = repoProgetti.get(a.idProgetto());
        if (p != null) {
            p.rimuoviAttivita(idAttivita);
            repoProgetti.salva(p);
        }
        repoAttivita.elimina(idAttivita);
    }

    public List<Attivita> listaTutte() { return repoAttivita.lista(); }
    public List<Attivita> perProgetto(String idProgetto) { return repoAttivita.perProgetto(idProgetto); }
}