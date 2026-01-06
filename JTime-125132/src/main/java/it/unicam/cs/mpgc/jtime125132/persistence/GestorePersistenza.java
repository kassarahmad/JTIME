package it.unicam.cs.mpgc.jtime125132.persistence;

import java.nio.file.Path;

public final class GestorePersistenza {

    private final Path path;
    private ArchivioDati archivio;

    public GestorePersistenza() {
        this.path = SalvataggioFile.pathDefault();
        this.archivio = SalvataggioFile.caricaSePresente(path);
        if (this.archivio == null) this.archivio = new ArchivioDati();
    }

    public ArchivioDati archivio() { return archivio; }

    public void salva() {
        SalvataggioFile.salva(path, archivio);
    }
}