package it.unicam.cs.mpgc.jtime125132.util;

import java.time.Duration;

public final class GestoreDate {
    private GestoreDate() {}
    public static String format(Duration d) {
        long m = d.toMinutes();
        long h = m / 60;
        long mm = m % 60;
        if (h <= 0) return mm + " min";
        return h + " h " + mm + " min";
    }
}