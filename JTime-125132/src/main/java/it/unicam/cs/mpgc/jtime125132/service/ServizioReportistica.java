package it.unicam.cs.mpgc.jtime125132.service;

import it.unicam.cs.mpgc.jtime125132.model.*;
import it.unicam.cs.mpgc.jtime125132.persistence.ArchivioDati;
import it.unicam.cs.mpgc.jtime125132.util.GestoreDate;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public final class ServizioReportistica {

    private final ArchivioDati archivio;

    public ServizioReportistica(ArchivioDati archivio) {
        this.archivio = Objects.requireNonNull(archivio);
    }

    public Report reportProgetto(String idProgetto) {
        var p = archivio.progetti().get(idProgetto);
        if (p == null) throw new IllegalArgumentException("Progetto non trovato.");

        var att = archivio.attivita().values().stream()
                .filter(a -> idProgetto.equals(a.idProgetto()))
                .toList();

        long tot = att.size();
        long comp = att.stream().filter(Attivita::completata).count();

        Duration st = att.stream().map(a -> a.stima().stimata()).reduce(Duration.ZERO, Duration::plus);
        Duration ef = att.stream()
                .map(a -> a.stima().effettiva() == null ? Duration.ZERO : a.stima().effettiva())
                .reduce(Duration.ZERO, Duration::plus);

        String txt = """
                REPORT PROGETTO
                Nome: %s
                Stato: %s
                Attività: %d (completate: %d)
                Tempo stimato: %s
                Tempo effettivo: %s
                Scostamento: %s
                """.formatted(
                p.nome(), p.stato(), tot, comp,
                GestoreDate.format(st),
                GestoreDate.format(ef),
                GestoreDate.format(ef.minus(st))
        );

        return new Report(txt);
    }

    public Report reportPianificazione(IntervalloTemporale intervallo) {
        Objects.requireNonNull(intervallo);
        LocalDate d = intervallo.da();
        LocalDate a = intervallo.a();

        StringBuilder sb = new StringBuilder();
        sb.append("REPORT PIANIFICAZIONE\n");
        sb.append("Intervallo: ").append(d).append(" → ").append(a).append("\n\n");

        var snap = archivio.pianificazione().snapshot();
        snap.entrySet().stream()
                .filter(e -> !(e.getKey().isBefore(d) || e.getKey().isAfter(a)))
                .sorted(Comparator.comparing(e -> e.getKey()))
                .forEach(e -> {
                    sb.append("• ").append(e.getKey()).append("\n");
                    for (String idAtt : e.getValue()) {
                        var att = archivio.attivita().get(idAtt);
                        if (att != null) sb.append("   - ").append(att.titolo()).append(" [").append(att.stato()).append("]\n");
                    }
                    sb.append("\n");
                });

        return new Report(sb.toString());
    }
}