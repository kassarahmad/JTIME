package it.unicam.cs.mpgc.jtime125132.controller;

import it.unicam.cs.mpgc.jtime125132.model.IntervalloTemporale;
import it.unicam.cs.mpgc.jtime125132.model.Report;
import it.unicam.cs.mpgc.jtime125132.service.ServizioReportistica;

import java.util.Objects;

public final class ControllerReport {

    private final ServizioReportistica servizio;

    public ControllerReport(ServizioReportistica servizio) {
        this.servizio = Objects.requireNonNull(servizio);
    }

    public Report reportProgetto(String idProgetto) { return servizio.reportProgetto(idProgetto); }
    public Report reportPianificazione(IntervalloTemporale i) { return servizio.reportPianificazione(i); }
}