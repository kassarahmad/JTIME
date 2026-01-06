package it.unicam.cs.mpgc.jtime125132.persistence;

import it.unicam.cs.mpgc.jtime125132.model.Progetto;

import java.util.List;
import java.util.Objects;

public final class RepositoryProgetti {
    private final ArchivioDati archivio;
    private final GestorePersistenza persistenza;

    public RepositoryProgetti(ArchivioDati archivio, GestorePersistenza persistenza) {
        this.archivio = Objects.requireNonNull(archivio);
        this.persistenza = Objects.requireNonNull(persistenza);
    }

    public void salva(Progetto p) {
        archivio.progetti().put(p.id(), p);
        persistenza.salva();
    }

    public Progetto get(String id) { return archivio.progetti().get(id); }

    public List<Progetto> lista() { return List.copyOf(archivio.progetti().values()); }

    public void elimina(String id) {
        archivio.progetti().remove(id);
        persistenza.salva();
    }
}