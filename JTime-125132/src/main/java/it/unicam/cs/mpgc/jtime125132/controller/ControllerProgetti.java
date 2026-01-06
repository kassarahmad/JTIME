package it.unicam.cs.mpgc.jtime125132.controller;

import it.unicam.cs.mpgc.jtime125132.model.Progetto;
import it.unicam.cs.mpgc.jtime125132.persistence.ArchivioDati;
import it.unicam.cs.mpgc.jtime125132.persistence.RepositoryAttivita;
import it.unicam.cs.mpgc.jtime125132.persistence.RepositoryProgetti;
import it.unicam.cs.mpgc.jtime125132.service.ServizioPianificazione;
import it.unicam.cs.mpgc.jtime125132.util.GeneratoreIdentificativi;
import it.unicam.cs.mpgc.jtime125132.util.ValidatoreInput;

import java.util.List;
import java.util.Objects;

public final class ControllerProgetti {

    private final ArchivioDati archivio;
    private final RepositoryProgetti repoProgetti;
    private final RepositoryAttivita repoAttivita;
    private final ServizioPianificazione servizioPianificazione;

    public ControllerProgetti(ArchivioDati archivio, RepositoryProgetti repoProgetti, RepositoryAttivita repoAttivita, ServizioPianificazione servizioPianificazione) {
        this.archivio = Objects.requireNonNull(archivio);
        this.repoProgetti = Objects.requireNonNull(repoProgetti);
        this.repoAttivita = Objects.requireNonNull(repoAttivita);
        this.servizioPianificazione = Objects.requireNonNull(servizioPianificazione);
    }

    public Progetto creaProgetto(String nome) {
        String n = ValidatoreInput.nonVuota(nome, "Nome progetto");
        Progetto p = new Progetto(GeneratoreIdentificativi.nuovoId(), n);
        repoProgetti.salva(p);
        return p;
    }

    public void rinomina(String idProgetto, String nuovoNome) {
        var p = Objects.requireNonNull(repoProgetti.get(idProgetto));
        p.rinomina(ValidatoreInput.nonVuota(nuovoNome, "Nuovo nome"));
        repoProgetti.salva(p);
    }

    public void chiudi(String idProgetto) {
        var p = Objects.requireNonNull(repoProgetti.get(idProgetto));
        boolean pendenti = repoAttivita.perProgetto(idProgetto).stream().anyMatch(a -> !a.completata());
        p.chiudi(pendenti);
        repoProgetti.salva(p);
    }

    public void eliminaProgetto(String idProgetto) {
        var idsAtt = repoAttivita.idsAttivitaPerProgetto(idProgetto);
        servizioPianificazione.rimuoviAttivitaDaTutteLeDate(idsAtt);
        repoAttivita.eliminaPerProgetto(idProgetto);
        repoProgetti.elimina(idProgetto);
    }

    public List<Progetto> lista() { return repoProgetti.lista(); }

    public ArchivioDati archivio() { return archivio; }
}