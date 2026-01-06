package it.unicam.cs.mpgc.jtime125132.persistence;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public final class SalvataggioFile {
    private SalvataggioFile() {}

    public static Path pathDefault() {
        String home = System.getProperty("user.home");
        return Path.of(home, ".jtime125132", "archivio.ser");
    }

    public static void salva(Path path, ArchivioDati archivio) {
        try {
            Files.createDirectories(path.getParent());
            try (var out = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(path)))) {
                out.writeObject(archivio);
            }
        } catch (IOException e) {
            throw new RuntimeException("Salvataggio fallito: " + e.getMessage(), e);
        }
    }

    public static ArchivioDati caricaSePresente(Path path) {
        if (!Files.exists(path)) return null;
        try (var in = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(path)))) {
            Object o = in.readObject();
            return (ArchivioDati) o;
        } catch (Exception e) {
            return null; // se file corrotto, si riparte da zero
        }
    }
}