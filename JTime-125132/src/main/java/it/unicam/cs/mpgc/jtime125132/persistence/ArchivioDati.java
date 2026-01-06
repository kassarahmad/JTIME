package it.unicam.cs.mpgc.jtime125132.persistence;

import it.unicam.cs.mpgc.jtime125132.model.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ArchivioDati implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    private final Map<String, Progetto> progetti = new LinkedHashMap<>();
    private final Map<String, Attivita> attivita = new LinkedHashMap<>();
    private final Pianificazione pianificazione = new Pianificazione();

    public Map<String, Progetto> progetti() { return progetti; }
    public Map<String, Attivita> attivita() { return attivita; }
    public Pianificazione pianificazione() { return pianificazione; }
}