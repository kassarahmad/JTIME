package it.unicam.cs.mpgc.jtime125132.util;

import java.util.UUID;

public final class GeneratoreIdentificativi {
    private GeneratoreIdentificativi() {}
    public static String nuovoId() { return UUID.randomUUID().toString(); }
}