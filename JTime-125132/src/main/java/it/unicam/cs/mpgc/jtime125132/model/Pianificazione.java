package it.unicam.cs.mpgc.jtime125132.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public final class Pianificazione implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    private final Map<LocalDate, LinkedHashSet<String>> mappa = new LinkedHashMap<>();

    public void pianifica(LocalDate data, String idAttivita) {
        mappa.computeIfAbsent(data, d -> new LinkedHashSet<>()).add(idAttivita);
    }

    public void rimuovi(LocalDate data, String idAttivita) {
        var set = mappa.get(data);
        if (set == null) return;
        set.remove(idAttivita);
        if (set.isEmpty()) mappa.remove(data);
    }

    public List<String> pianificate(LocalDate data) {
        var set = mappa.getOrDefault(data, new LinkedHashSet<>());
        return List.copyOf(set);
    }

    public void rimuoviAttivitaDaTutteLeDate(Set<String> ids) {
        if (ids == null || ids.isEmpty()) return;
        for (var entry : new ArrayList<>(mappa.entrySet())) {
            entry.getValue().removeIf(ids::contains);
            if (entry.getValue().isEmpty()) mappa.remove(entry.getKey());
        }
    }

    public Map<LocalDate, List<String>> snapshot() {
        Map<LocalDate, List<String>> out = new LinkedHashMap<>();
        for (var e : mappa.entrySet()) out.put(e.getKey(), List.copyOf(e.getValue()));
        return out;
    }
}