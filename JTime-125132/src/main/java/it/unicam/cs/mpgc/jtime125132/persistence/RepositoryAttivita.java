package it.unicam.cs.mpgc.jtime125132.persistence;

import it.unicam.cs.mpgc.jtime125132.model.Attivita;

import java.util.*;
import java.util.stream.Collectors;

public final class RepositoryAttivita {
    private final ArchivioDati archivio;
    private final GestorePersistenza persistenza;

    public RepositoryAttivita(ArchivioDati archivio, GestorePersistenza persistenza) {
        this.archivio = Objects.requireNonNull(archivio);
        this.persistenza = Objects.requireNonNull(persistenza);
    }

    public void salva(Attivita a) {
        archivio.attivita().put(a.id(), a);
        persistenza.salva();
    }

    public Attivita get(String id) { return archivio.attivita().get(id); }

    public List<Attivita> lista() { return List.copyOf(archivio.attivita().values()); }

    public List<Attivita> perProgetto(String idProgetto) {
        return archivio.attivita().values().stream()
                .filter(a -> idProgetto.equals(a.idProgetto()))
                .toList();
    }

    public Set<String> idsAttivitaPerProgetto(String idProgetto) {
        return perProgetto(idProgetto).stream().map(Attivita::id).collect(Collectors.toSet());
    }

    public void elimina(String idAttivita) {
        archivio.attivita().remove(idAttivita);
        persistenza.salva();
    }

    public void eliminaPerProgetto(String idProgetto) {
        idsAttivitaPerProgetto(idProgetto).forEach(id -> archivio.attivita().remove(id));
        persistenza.salva();
    }
}